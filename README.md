# JMS 1.1 Workshop

A few samples describing main topics of JMS 1.1(jsr 914):
* Queues
* Topics
* DLQs and ExpiryQueues
* _Sync_ Message

[Slides pt-BR](https://docs.google.com/presentation/d/1xnRC5N1mYP-237TUxb0tDPjjVg9Qi905CL57V-ken-E/edit#slide=id.g1d639e6386_1_361)

## Environment

* Java 8
* JBoss EAP 6.4
* HornetQ

## Configuration

JBoss **standalone-full** profile.

### Security

For the sake of simplicity, security is off:

```xml
<hornetq-server>
<!-- etc -->
    <security-enabled>false</security-enabled>
<!-- etc -->
</hornetq-server>
```

### Destinations

```xml
<hornetq-server>
<!-- etc -->
<address-settings>
    <address-setting match="jms.queue.checkoutQueue">
        <dead-letter-address>jms.queue.checkoutDLQ</dead-letter-address>
        <redelivery-delay>5000</redelivery-delay>
        <max-delivery-attempts>3</max-delivery-attempts>
    </address-setting>
</address-settings>
<!-- etc -->
<jms-destinations>

    <!-- etc -->
    <jms-queue name="checkoutQueue">
        <entry name="java:/jboss/exported/jms/queue/checkout"/>
        <durable>true</durable>
    </jms-queue>
    <jms-queue name="premiumCheckoutQueue">
        <entry name="java:/jboss/exported/jms/queue/premiumCheckout"/>
        <durable>true</durable>
    </jms-queue>
    <jms-queue name="checkoutDLQ">
        <entry name="java:/jms/queue/checkoutDLQ"/>
        <durable>true</durable>
    </jms-queue>
    <jms-queue name="SyncQueue">
        <entry name="java:/jboss/exported/jms/queue/sync"/>
        <durable>true</durable>
    </jms-queue>
    <jms-topic name="notificationTopic">
        <entry name="java:/jboss/exported/jms/topic/metrics"/>
    </jms-topic>
</jms-destinations>
</hornetq-server>

```
