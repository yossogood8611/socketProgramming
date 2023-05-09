module com.chatting.projectchatting {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports com.chatting.projectchatting.client;
    exports com.chatting.projectchatting.server;
    opens com.chatting.projectchatting.client to javafx.fxml;
    opens com.chatting.projectchatting.server to javafx.fxml;
}
