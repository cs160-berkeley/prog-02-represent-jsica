package com.example.jessica.reps;

/**
 * Created by Jessica on 3/4/16.
 */
public class rep {
    public String n, mail, web, city ,tweet, picid, vote, term, percent, party, comm, bills;

    public rep(String name, String m, String w, String c, String t, String pid, String start, String aff) {
        this.n = name;
        this.mail = m;
        this.web = w;
        this.city = c;
        this.tweet = t;
        this.picid = pid;
        this.term = start;
        this.party = aff;
    }

    public rep() {
        super();
    }

    public void setVote(String v1) {
        this.vote = v1;
    }

    public void setPercent(String p) {
        this.percent = p;
    }

    public void setComm(String p) {
        this.comm = p;
    }

    public void setBills(String p) {
        this.bills = p;
    }

}
