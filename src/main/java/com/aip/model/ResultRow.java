package com.aip.model;

/** Simple view object: a candidate with their vote count. */
public class ResultRow {
    private final Long id;
    private final String name;
    private final String party;
    private final long votes;

    public ResultRow(Long id, String name, String party, long votes) {
        this.id = id; this.name = name; this.party = party; this.votes = votes;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getParty() { return party; }
    public long getVotes() { return votes; }
}
