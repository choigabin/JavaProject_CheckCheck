package kr.hs.emirim.gaebi.java_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {

    private Button jointBtn;
    private EditText login_email, login_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_join);

        mAuth = FirebaseAuth.getInstance();

        //findViewById
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        findViewById(R.id.jointBtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //회원가입 기능을 구현한 signUp() 메서드 호출
            signUp();
        }
    };
    private void signUp() {
        //xml에 선언한 login_email(EditText)의 텍스트를 String타입으로 변환하여 email 변수에 넣음
        String email = ((EditText)findViewById(R.id.login_email)).getText().toString();
        //xml에 선언한 login_password(EditText)의 텍스트를 String타입으로 변환하여 password 변수에 넣음
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();

        //FirebaseAuth의 기능인 createUserWithEmailAndPassword을 사용해 회원가입 기능 구현
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //회원가입 성공시, "회원가입이 완료되었습니다!" 알림
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다!", Toast.LENGTH_LONG).show();
                            //사용자 등록을 완료하면 ChoiceActivity로 이동
                            Intent intent = new Intent(JoinActivity.this, ChoiceActivity.class);
                            startActivity(intent);
                        }
                        //회원가입 실패시, "회원가입에 실패하였습니다." 알림
                        else {
                            Toast.makeText(JoinActivity.this, "회원가입에 실패하였습니다", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
