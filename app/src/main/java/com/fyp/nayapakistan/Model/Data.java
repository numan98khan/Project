package com.fyp.nayapakistan.Model;

import java.util.List;

public class Data {
    public String title;
    public String body;

    public Data(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class FCMResponse {
        public long multicast_id;
        public int success;
        public int failture;
        public int canonical_ids;
        public List<Result> results;

        public FCMResponse() {
        }

        public FCMResponse(long multicast_id, int success, int failture, int canonical_ids, List<Result> results) {
            this.multicast_id = multicast_id;
            this.success = success;
            this.failture = failture;
            this.canonical_ids = canonical_ids;
            this.results = results;
        }

        public long getMulticast_id() {
            return multicast_id;
        }

        public void setMulticast_id(long multicast_id) {
            this.multicast_id = multicast_id;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getFailture() {
            return failture;
        }

        public void setFailture(int failture) {
            this.failture = failture;
        }

        public int getCanonical_ids() {
            return canonical_ids;
        }

        public void setCanonical_ids(int canonical_ids) {
            this.canonical_ids = canonical_ids;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }
}
