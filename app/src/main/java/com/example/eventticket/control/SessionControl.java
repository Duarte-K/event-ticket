package com.example.eventticket.control;

import android.content.Context;
import android.content.Intent;

import com.example.eventticket.activities.MainActivity;
import com.example.eventticket.model.SessionModel;

public class SessionControl {
    private SessionModel session;
    private final Context context;

    public SessionControl(Context contxt){
        this.session = new SessionModel(contxt);
        this.context = contxt;
    }

    public void checkSession(){
        if(session.getUserName().equals(null) || session.getUserName().equals("")){
            logout();
        }
    }
    public void logout(){
        session.setUserName("");
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public String userSession(){
        return session.getUserName();
    }
}
