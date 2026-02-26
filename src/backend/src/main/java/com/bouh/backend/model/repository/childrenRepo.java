package com.bouh.backend.model.repository;

import com.bouh.backend.model.Dto.childDto;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class childrenRepo {

    private final Firestore firestore;

    public childrenRepo(Firestore firestore) {
        this.firestore = firestore;
    }

    private CollectionReference childrenCollection(String caregiverId) {
        return firestore.collection("caregivers")
                .document(caregiverId)
                .collection("children");
    }

    public List<childDto> getChildren(String caregiverId) throws ExecutionException, InterruptedException {
        var result = new ArrayList<childDto>();

        var snap = childrenCollection(caregiverId).get().get();
        for (DocumentSnapshot doc : snap.getDocuments()) {
            var dto = new childDto();
            dto.setChildID(doc.getId());
            dto.setName(getString(doc, "name"));
            dto.setDateOfBirth(getString(doc, "dateOfBirth"));
            dto.setGender(getString(doc, "gender"));
            result.add(dto);
        }
        return result;
    }

    public int countChildren(String caregiverId) throws ExecutionException, InterruptedException {
        return childrenCollection(caregiverId).get().get().size();
    }

    public childDto getChildById(String caregiverId, String childId) throws ExecutionException, InterruptedException {
        var ref = childrenCollection(caregiverId).document(childId);
        var doc = ref.get().get();
        if (doc == null || !doc.exists()) return null;

        var dto = new childDto();
        dto.setChildID(childId);
        dto.setName(getString(doc, "name"));
        dto.setDateOfBirth(getString(doc, "dateOfBirth"));
        dto.setGender(getString(doc, "gender"));
        return dto;
    }

    public childDto addChild(String caregiverId, String name, String dateOfBirth, String gender)
            throws ExecutionException, InterruptedException {

        DocumentReference newRef = childrenCollection(caregiverId).document(); // auto ID

        newRef.set(new java.util.HashMap<String, Object>() {{
            put("name", name);
            put("dateOfBirth", dateOfBirth);
            put("gender", gender);
            put("createdAt", FieldValue.serverTimestamp());
            put("updatedAt", FieldValue.serverTimestamp());
        }}).get();

        return getChildById(caregiverId, newRef.getId());
    }

    public childDto updateChild(String caregiverId, String childId, java.util.Map<String, Object> updates)
            throws ExecutionException, InterruptedException {

        var ref = childrenCollection(caregiverId).document(childId);
        var doc = ref.get().get();
        if (doc == null || !doc.exists()) return null;

        updates.put("updatedAt", FieldValue.serverTimestamp());
        ref.update(updates).get();

        return getChildById(caregiverId, childId);
    }

    public boolean deleteChild(String caregiverId, String childId) throws ExecutionException, InterruptedException {
        var ref = childrenCollection(caregiverId).document(childId);
        var doc = ref.get().get();
        if (doc == null || !doc.exists()) return false;

        ref.delete().get();
        return true;
    }

    private static String getString(DocumentSnapshot doc, String field) {
        Object v = doc.get(field);
        return v == null ? null : v.toString();
    }
}