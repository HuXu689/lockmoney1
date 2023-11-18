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
    public FirebaseAuth getFirebase(){
        return fAuth;
    }

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
        DocumentReference df = allUserCollectionReference().document(uid);

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
        DocumentReference df = allUserCollectionReference().document(uid);

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
    public interface returnError {
        void OnReturnResult(String error);
    }
    public interface returnStringResult {
        void OnReturnResult(String result);
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
                                document.getString("role_id"),
                                Integer.parseInt(document.getString("status") != null ? document.getString("status"): "1")
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
                        if (document.getLong("status").intValue() == 0)
                            continue;
                        UserModel user = new UserModel(
                                document.getString("user_id"),
                                document.getString("userName"),
                                document.getString("userFullName"),
                                document.getString("password"),
                                document.getString("email"),
                                document.getString("role_id"),
                                document.getLong("status").intValue()
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


    private void findEmailByUsername(String username, returnStringResult callback) {
        allUserCollectionReference()
                .whereEqualTo("userName", username)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Tìm thấy người dùng với username
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        String email = documentSnapshot.getString("email");
                        callback.OnReturnResult(email);
                    } else {
                        // Không tìm thấy người dùng với username
                        callback.OnReturnResult(null);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi truy vấn dữ liệu
                    callback.OnReturnResult(null);
                });
    }
    public void loginUser(String text_login_username, String text_login_password, returnResult callback, returnError error) {
        // Tìm email từ username
        findEmailByUsername(text_login_username, new returnStringResult() {
            @Override
            public void OnReturnResult(String email) {
                if (email != null) {
                    // Sử dụng email để đăng nhập
                    fAuth.signInWithEmailAndPassword(email, text_login_password)
                            .addOnSuccessListener(authResult -> {
                                checkUserStatus(authResult.getUser().getUid(), new returnResult() {
                                    @Override
                                    public void OnReturnResult(int status) {
                                        if (status == 1) {
                                            isAdmin(authResult.getUser().getUid(), new returnResult() {
                                                @Override
                                                public void OnReturnResult(int result) {
                                                    if (result == 1){
                                                            callback.OnReturnResult(2);
                                                    }
                                                    else
                                                        callback.OnReturnResult(1);
                                                }
                                            });
                                        } else {
                                            // Tài khoản có status = 0, không cho phép đăng nhập
                                            error.OnReturnResult("Tài khoản bị vô hiệu");
                                            callback.OnReturnResult(0);
                                        }
                                    }
                                });
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý lỗi khi đăng nhập không thành công
                                error.OnReturnResult("Đăng nhập không thành công");
                                callback.OnReturnResult(0);
                            });
                } else {
                    // Username không tồn tại
                    error.OnReturnResult("username không tồn tại");
                    callback.OnReturnResult(0);
                }
            }
        });
    }

    private void checkUserStatus(String userId, returnResult callback) {
        allUserCollectionReference().document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Nếu tài khoản tồn tại
                        int status = documentSnapshot.getLong("status").intValue();
                        callback.OnReturnResult(status);
                    } else {
                        // Nếu tài khoản không tồn tại hoặc có lỗi khi đọc dữ liệu
                        callback.OnReturnResult(0);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi truy vấn dữ liệu
                    callback.OnReturnResult(0);
                });
    }

    public void registerUser(String username, String fullname, String password, String email, returnResult callback){
        // Kiểm tra xem username đã tồn tại chưa
        checkUsernameAvailability(username, new returnResult() {
            @Override
            public void OnReturnResult(int availability) {
                if (availability == 1) {
                    // Username đã tồn tại, không thể đăng ký
                    callback.OnReturnResult(-1);
                } else {
                    // Đăng ký người dùng mới
                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
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
                                    userInfo.put("status", 1);
                                    df.set(userInfo);
                                    result = 1;
                                }
                                callback.OnReturnResult(result);
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý lỗi khi đăng ký không thành công
                                callback.OnReturnResult(0);
                            });
                }
            }
        });
    }

    private void checkUsernameAvailability(String username, returnResult callback) {
        allUserCollectionReference()
                .whereEqualTo("userName", username)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Username đã tồn tại
                        callback.OnReturnResult(1);
                    } else {
                        // Username chưa được sử dụng
                        callback.OnReturnResult(0);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi truy vấn dữ liệu
                    callback.OnReturnResult(0);
                });
    }

    public void deleteUser(String userId, returnResult callback) {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("status", 0);

        allUserCollectionReference().document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    callback.OnReturnResult(1);
                })
                .addOnFailureListener(e -> {
                    callback.OnReturnResult(0);
                });
    }


    public DAO(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

}
