package com.jmethods.catatumbo.impl;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.entities.Address;
import com.jmethods.catatumbo.entities.SuperClass5;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmbeddableIntrospectorTest {

    @Test
    public void introspect_embeddableClass() {
        EmbeddableMetadata metadata = EmbeddableIntrospector.introspect(Address.class);
        assertEquals(5, metadata.getPropertyMetadataCollection().size());

        List<String> propertyMetadataMappedNames = metadata.getPropertyMetadataCollection().stream().map(propertyMetadata -> propertyMetadata.getMappedName()).collect(Collectors.toList());
        assertTrue(propertyMetadataMappedNames.containsAll(Arrays.asList("line1", "line2", "city", "state", "postal_code")));
    }

    @Test
    public void introspect_embeddableMappedSuperClass() {
        EmbeddableMetadata metadata = EmbeddableIntrospector.introspect(SuperClass5.class);
        assertEquals(7, metadata.getPropertyMetadataCollection().size());

        List<String> superClass5Properties = new ArrayList<>(Arrays.asList("createdBy", "createdOn", "modifiedBy", "modifiedOn"));
        List<String> superSuperClassProperties = new ArrayList<>(Arrays.asList("super.super", "myKey", "address"));
        superClass5Properties.addAll(superSuperClassProperties);

        List<String> propertyMetadataMappedNames = metadata.getPropertyMetadataCollection().stream().map(propertyMetadata -> propertyMetadata.getMappedName()).collect(Collectors.toList());
        assertTrue(propertyMetadataMappedNames.containsAll(superClass5Properties));
    }
}
