package io.github.rainvaporeon.chessfx.events;

import com.spiritlight.fishutils.utils.eventbus.events.Event;
import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.events.bus.FXEventBus;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.*;

public class FXEvents {

    private static final Map<Node, EnumSet<EnumInteractionHandle>> handlerMap = new WeakHashMap<>();

    static {
        FXEventBus.INSTANCE.subscribe(FXEvents.class);
    }

    /**
     * Appends interaction to this node, such that it follows
     * the drag interaction: on drag, follows the mouse cursor
     * and fires a {@link MouseDragReleaseEvent} when the drag
     * releases.
     * @param node the node to apply this interaction handle to
     */
    public static void appendDragInteractions(Node node) {
        if(!handlerMap.containsKey(node)) addHandle(node);
        handlerMap.get(node).add(EnumInteractionHandle.DRAG);
    }

    private static void addHandle(Node node) {
        handlerMap.putIfAbsent(node, EnumSet.noneOf(EnumInteractionHandle.class));
    }

    @EventBusSubscriber
    private static void onDragOrRelease(ClickEvent event) {
        // no-op
        // TODO: Enable drag functionality if regular moving works
    }

    private static void processDrag(ClickEvent source) {
        for(Node node : handlerMap.keySet()) {
            if(handlerMap.get(node).contains(EnumInteractionHandle.DRAG)) {
                Bounds bound = node.getBoundsInLocal();
                if(bound.contains(source.getContext().x(), source.getContext().y())) {
                    // TODO: Process drag action
                    Dragboard dragboard = node.startDragAndDrop(TransferMode.ANY);


                }
            }
        }
    }


    public static class MouseDragReleaseEvent extends FXEvent {
        private final Node node;

        public MouseDragReleaseEvent(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }

    private enum EnumInteractionHandle {
        DRAG,
        CLICK,
    }
}
