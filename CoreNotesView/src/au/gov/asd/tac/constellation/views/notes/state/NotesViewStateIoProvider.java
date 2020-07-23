/*
 * Copyright 2010-2020 Australian Signals Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.gov.asd.tac.constellation.views.notes.state;

import au.gov.asd.tac.constellation.graph.Attribute;
import au.gov.asd.tac.constellation.graph.GraphReadMethods;
import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import au.gov.asd.tac.constellation.graph.NotesConcept;
import au.gov.asd.tac.constellation.graph.attribute.io.AbstractGraphIOProvider;
import au.gov.asd.tac.constellation.graph.attribute.io.GraphByteReader;
import au.gov.asd.tac.constellation.graph.attribute.io.GraphByteWriter;
import au.gov.asd.tac.constellation.utilities.datastructure.ImmutableObjectCache;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 * IO provider for the NOTES_VIEW_STATE object.
 *
 * @author aldebaran30701
 */
@ServiceProvider(service = AbstractGraphIOProvider.class)
public class NotesViewStateIoProvider extends AbstractGraphIOProvider {

    @Override
    public String getName() {
        return NotesConcept.MetaAttribute.NOTES_VIEW_STATE.getName();
    }

    @Override
    public void readObject(final int attributeId, final int elementId, final JsonNode jnode,
            final GraphWriteMethods graph, final Map<Integer, Integer> vertexMap, final Map<Integer, Integer> transactionMap,
            final GraphByteReader byteReader, final ImmutableObjectCache cache) throws IOException {
        if (!jnode.isNull()) {
            final List<NotesViewEntry> noteEntries = new ArrayList<>();
            final ArrayNode notesArray = (ArrayNode) jnode.withArray("notes");
            for (int i = 0; i < notesArray.size(); i++) {
                if (notesArray.get(i).isNull()) {
                    noteEntries.add(null);
                } else {
                    // create NotesViewEntry with index, visibility, query and description
                    noteEntries.add(new NotesViewEntry(
                            notesArray.get(i).get(0).asBoolean(),
                            notesArray.get(i).get(1).asText(),
                            notesArray.get(i).get(2).asText(),
                            notesArray.get(i).get(3).asText()
                    ));
                }
            }
            final NotesViewState state = new NotesViewState(noteEntries);
            graph.setObjectValue(attributeId, elementId, state);
        }
    }

    @Override
    public void writeObject(final Attribute attribute, final int elementId, final JsonGenerator jsonGenerator,
            final GraphReadMethods graph, final GraphByteWriter byteWriter, final boolean verbose) throws IOException {

        if (verbose || !graph.isDefaultValue(attribute.getId(), elementId)) {
            final NotesViewState originalState = graph.getObjectValue(attribute.getId(), elementId);

            if (originalState == null) {
                jsonGenerator.writeNullField(attribute.getName());
            } else {
                // make a copy in case the state on the graph is currently being modified.
                final NotesViewState state = new NotesViewState(originalState);
                jsonGenerator.writeObjectFieldStart(attribute.getName());
                jsonGenerator.writeArrayFieldStart("notes");

                for (NotesViewEntry note : state.getNotes()) {
                    if (note == null) {
                        jsonGenerator.writeNull();
                    } else {
                        jsonGenerator.writeStartArray();
                        jsonGenerator.writeBoolean(note.isUserNote());
                        jsonGenerator.writeString(note.getTimestamp());
                        jsonGenerator.writeString(note.getNoteTitle());
                        jsonGenerator.writeString(note.getNoteContent());
                        jsonGenerator.writeEndArray();
                    }
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.writeEndObject();
            }
        }
    }
}
