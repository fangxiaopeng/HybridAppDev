package com.example.agentweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;

public class MainActivity extends AppCompatActivity {

    private AgentWeb mAgentWeb;

    private LinearLayout agentWebLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agentWebLayout = (LinearLayout)findViewById(R.id.web_agent_layout) ;


        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(agentWebLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://www.jd.com");
    }



}
