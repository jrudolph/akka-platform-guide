package shopping.analytics;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;

public class Main extends AbstractBehavior<Void> {

    public static void main(String[] args) throws Exception {
        ActorSystem<Void> system = ActorSystem.create(Main.create(), "ShoppingAnalyticsService");
    }

    public static Behavior<Void> create() {
        return Behaviors.setup(Main::new);
    }

    public Main(ActorContext<Void> context) {
        super(context);

        ActorSystem<?> system = context.getSystem();

        // FIXME no get(ClassicActorSystemProvider) for Java?
        AkkaManagement.get(system.classicSystem()).start();
        ClusterBootstrap.get(system.classicSystem()).start();

        ShoppingCartEventConsumer.init(system);

    }

    @Override
    public Receive<Void> createReceive() {
        return newReceiveBuilder().build();
    }
}
