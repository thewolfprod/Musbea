package tw.musbea.chat;

import java.util.List;

import tw.musbea.account.User;

public class Chat {
    private String chatUid;
    private String createdByUserUid;
    private String createdByUsername;
    private String chatName;
    private List<User> chatMembers;
    private List<Message> chatMessages;

    public String getChatUid() {
        return chatUid;
    }

    public void setChatUid(String chatUid) {
        this.chatUid = chatUid;
    }

    public String getCreatedByUserUid() {
        return createdByUserUid;
    }

    public void setCreatedByUserUid(String createdByUserUid) {
        this.createdByUserUid = createdByUserUid;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<User> getChatMembers() {
        return chatMembers;
    }

    public void setChatMembers(List<User> chatMembers) {
        this.chatMembers = chatMembers;
    }

    public List<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
