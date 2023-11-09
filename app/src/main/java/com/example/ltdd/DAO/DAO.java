package com.example.ltdd.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ltdd.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    public String currentUserId(){return fAuth.getUid();}
    public DocumentReference currentUserDetails(){
        return fStore.collection("User").document(currentUserId());
    }
    public CollectionReference allUserCollectionReference(){
        return fStore.collection("User");
    }
    public boolean isLoggedIn(){
        if(currentUserId() != null)
            return true;
        else
            return false;
    }
    public void isAdmin(String uid, returnResult callback){
        DocumentReference df = fStore.collection("User").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int isAdmin = 0;
                if (documentSnapshot.exists()) {
                    String roleId = documentSnapshot.getString("role_id");
                    if ("1".equals(roleId)) {
                        isAdmin = 1;
                    }
                }
                callback.OnReturnResult(isAdmin);
            }
        });
    }
    public void isAdmin(returnResult callback){
        String uid = fAuth.getCurrentUser().getUid();
        DocumentReference df = fStore.collection("User").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int isAdmin = 0;
                if (documentSnapshot.exists()) {
                    String roleId = documentSnapshot.getString("role_id");
                    if ("1".equals(roleId)) {
                        isAdmin = 1;
                    }
                }
                callback.OnReturnResult(isAdmin);
            }
        });
    }

    public interface returnResult {
        void OnReturnResult(int result);
    }
    public void getUsers(String user_id, final OnUserListener listener) {
        CollectionReference usersCollection = allUserCollectionReference();
        Query query = usersCollection.whereGreaterThanOrEqualTo("user_id", user_id);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                UserModel user = new UserModel();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        user = new UserModel(
                                document.getString("user_id"),
                                document.getString("userName"),
                                document.getString("userFullName"),
                                document.getString("password"),
                                document.getString("email"),
                                document.getString("role_id")
                        );
                    }
                    listener.onUserReceived(user);
                } else {
                    listener.onUserError("No documents found.");
                }
            } else {
                listener.onUserError(task.getException().getMessage());
            }
        });
    }


    public void getListUsers(String username, final OnUserListListener listener) {

        CollectionReference usersCollection = allUserCollectionReference();
        Query query = usersCollection.whereGreaterThanOrEqualTo("userName", username);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    ArrayList<UserModel> userList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        UserModel user = new UserModel(
                                document.getString("user_id"),
                                document.getString("userName"),
                                document.getString("userFullName"),
                                document.getString("password"),
                                document.getString("email"),
                                document.getString("role_id")
                        );
                        userList.add(user);
                    }
                    listener.onUserListReceived(userList);
                } else {
                    listener.onUserListError("No documents found.");
                }
            } else {
                listener.onUserListError(task.getException().getMessage());
            }
        });
    }
    public interface OnUserListListener {
        void onUserListReceived(ArrayList<UserModel> userList);

        void onUserListError(String error);
    }
    public interface OnUserListener {
        void onUserReceived(UserModel user);

        void onUserError(String error);
    }




    public void loginUser(String text_login_username, String text_login_password, returnResult callback){
        Log.d("TAG", "onClick: "+ text_login_username);

        fAuth.signInWithEmailAndPassword(text_login_username, text_login_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                int result = 0;
                isAdmin(authResult.getUser().getUid(), new returnResult() {
                    @Override
                    public void OnReturnResult(int result) {
                        callback.OnReturnResult(result);
                    }
                });

                callback.OnReturnResult(result);
            }
        });
    }
    public void registerUser(String username, String fullname, String password, String email, returnResult callback){
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                int result = 0;
                if (task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("User").document(user.getUid());
                    HashMap<String, Object> userInfo = new HashMap<>();
                    userInfo.put("user_id", user.getUid());
                    userInfo.put("role_id", "0");
                    userInfo.put("userName", username);
                    userInfo.put("userFullName", fullname);
                    userInfo.put("password", password);
                    userInfo.put("email", email);
                    df.set(userInfo);

                    result = 1;
                }
                callback.OnReturnResult(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.OnReturnResult(0);
            }
        });
    }
    private void deleteUser(String userId) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", 0);

        allUserCollectionReference().document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Cập nhật status thành công

                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi cập nhật status
                });
    }


    public DAO(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

}
