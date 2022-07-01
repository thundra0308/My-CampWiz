package limbo.mrvoid.mrmessenger.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import limbo.mrvoid.mrmessenger.activities.BaseActivity
import limbo.mrvoid.mrmessenger.activities.SignInActivity
import limbo.mrvoid.mrmessenger.activities.SignUpActivity
import limbo.mrvoid.mrmessenger.models.TeacherUser
import limbo.mrvoid.mrmessenger.utils.Constants

class FirestoreClass: BaseActivity() {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, teacherUserInfo: TeacherUser) {
        mFireStore.collection(Constants.Users).document(getCurrentUserId()).set(teacherUserInfo,SetOptions.merge()).addOnSuccessListener {
            activity.userRegisteredSuccess()
        }.addOnFailureListener {
            Log.e(activity.javaClass.simpleName, "Error Writing Document")
        }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser!=null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun loadUserData(activity: Activity, reaBoardList: Boolean = false) {
        mFireStore.collection(Constants.Users).document(getCurrentUserId()).get().addOnSuccessListener { document ->
            val loggedInTeacherUser = document.toObject(TeacherUser::class.java)
            if(loggedInTeacherUser!=null) {
                when (activity) {
                    is SignInActivity -> {
                            activity.signInSuccess(loggedInTeacherUser)
                    }
//                    is MainActivity -> {
//                        activity.updateNavigationUserDetails(loggedInUser,reaBoardList)
//                    }
//                    is MyProfileActivity -> {
//                        activity.setUserDataInUI(loggedInUser)
//                    }
                }
            }
        }.addOnFailureListener {
            e ->
            when (activity) {
                is SignInActivity -> {
                    activity.hideProgressDialog()
                }
//                is MainActivity -> {
//                    activity.hideProgressDialog()
//                }
            }
            Log.e(activity.javaClass.simpleName,"Error Writin Document", e)
        }
    }
//
//    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String,Any>) {
//        mFireStore.collection(Constants.Users).document(getCurrentUserId()).update(userHashMap).addOnSuccessListener {
//            Log.i(activity.javaClass.simpleName,"Profile Data Updated")
//            Toast.makeText(activity,"Profile Updated Successfully!",Toast.LENGTH_SHORT).show()
//            activity.profileUpdateSuccess()
//        }.addOnFailureListener {
//            e->
//            activity.hideProgressDialog()
//            Log.e(activity.javaClass.simpleName,"Error While Creating a Board",e)
//            Toast.makeText(activity,"Error When Updating the Profile!!",Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun createBoard(activity: CreateBoardActivity, board: Board) {
//        mFireStore.collection(Constants.BOARDS).document().set(board,SetOptions.merge()).addOnSuccessListener {
//            Log.e(activity.javaClass.simpleName,"Board Created Successfully!")
//            Toast.makeText(activity,"Board Created Successfully",Toast.LENGTH_SHORT).show()
//            activity.boardCreatedSuccessfully()
//        }.addOnFailureListener {
//            Log.e(activity.javaClass.simpleName, "Error Writing Document")
//        }
//    }
//
//    fun getBoardList(activity: MainActivity) {
//        mFireStore.collection(Constants.BOARDS).whereArrayContains(Constants.ASSIGNED_TO, getCurrentUserId()).get().addOnSuccessListener {
//            document->
//            Log.i(activity.javaClass.simpleName, document.documents.toString())
//            val boardList: ArrayList<Board> = ArrayList()
//                for(i in document.documents) {
//                    val board = i.toObject(Board::class.java)!!
//                    board.documentId = i.id
//                    boardList.add(board)
//                }
//            activity.populateBoardListToUI(boardList)
//        }.addOnFailureListener {
//            hideProgressDialog()
//            Log.e(activity.javaClass.simpleName,"Error While Creating a Board",it)
//        }
//    }
//
//    fun getBoardDetails(activity: TaskListActivity, documentId: String) {
//        mFireStore.collection(Constants.BOARDS).document(documentId).get().addOnSuccessListener {
//            document->
//            Log.i(activity.javaClass.simpleName,document.toString())
//            val board = document.toObject(Board::class.java)
//            board?.documentId = document.id
//            activity.boardDetails(board!!)
//        }
//    }
//
//    fun addUpdateTaskList(activity: Activity,board: Board) {
//        val taskListHashMap = HashMap<String,Any>()
//        taskListHashMap[Constants.TASK_LIST] = board.taskList
//        mFireStore.collection(Constants.BOARDS).document(board.documentId).update(taskListHashMap).addOnSuccessListener {
//            Log.e(activity.javaClass.simpleName, "TaskList Updated")
//            if(activity is TaskListActivity) {
//                activity.addUpdateTaskListSuccess()
//            } else if(activity is CardDetailsActivity) {
//                activity.addUpdateTaskListSuccess()
//            }
//        }.addOnFailureListener {
//            e->
//            if(activity is TaskListActivity) {
//                activity.hideProgressDialog()
//            } else if(activity is CardDetailsActivity) {
//                activity.hideProgressDialog()
//            }
//            Log.e(activity.javaClass.simpleName,"Error While Creating TaskList",e)
//        }
//    }
//
//    fun getAssignedMembersListDetails(activity: Activity, assignedTo: ArrayList<String>){
//        mFireStore.collection(Constants.Users).whereIn(Constants.ID, assignedTo).get().addOnSuccessListener {
//            document ->
//            Log.e(activity.javaClass.simpleName, document.documents.toString())
//            val userList: ArrayList<TeacherUser> = ArrayList()
//            for(i in document.documents) {
//                val user = i.toObject(TeacherUser::class.java)!!
//                userList.add(user)
//            }
//            if(activity is MembersActivity) {
//                activity.setupMemberRecyclerView(userList)
//            } else if(activity is TaskListActivity) {
//                activity.boardMemberDetailsList(userList)
//            }
//        }.addOnFailureListener {
//            if(activity is MembersActivity) {
//                activity.hideProgressDialog()
//            } else if(activity is TaskListActivity) {
//                activity.hideProgressDialog()
//            }
//            Log.e(activity.javaClass.simpleName,"Error While Creating Members List,")
//
//        }
//    }
//
//    fun getMemberDetails(activity: MembersActivity, email: String) {
//        mFireStore.collection(Constants.Users).whereEqualTo(Constants.EMAIL,email).get().addOnSuccessListener {
//            document->
//            if(document.documents.size>0) {
//                val user = document.documents[0].toObject(TeacherUser::class.java)!!
//                activity.membersDetails(user)
//            } else {
//                activity.hideProgressDialog()
//                activity.showErrorSnackBar("No Such Member Found!!")
//            }
//        }.addOnFailureListener {
//            e->
//            activity.hideProgressDialog()
//            Log.e(activity.javaClass.simpleName,"Error While Getting TeacherUser Details!!",e)
//        }
//    }
//
//    fun assignMembersToBoard(activity: MembersActivity, board: Board, user: TeacherUser) {
//        val assignedToHashMap = HashMap<String,Any>()
//        assignedToHashMap[Constants.ASSIGNED_TO] = board.assignedTo
//        mFireStore.collection(Constants.BOARDS).document(board.documentId).update(assignedToHashMap).addOnSuccessListener {
//            activity.memberAssignedSuccess(user)
//        }.addOnFailureListener {
//            e->
//            activity.hideProgressDialog()
//            Log.e(activity.javaClass.simpleName,"Error Assigning Members To Board!!",e)
//        }
//    }

}