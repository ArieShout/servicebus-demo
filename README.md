# Service Bus example for Session support

## Setup Service Bus

1. In Azure Portal, click "Create a resource" on the top left
1. Search for "Service Bus"
1. Create. Note you need to select at least the Standard tier to get the message session support.
1. When the Service Bus is created, go to the resource and go to "Queues" blade
1. Click "+ Queue" on the top
1. Make sure "Enable sessions" is checked, and create the queue
1. When the queue is created, click the queue, then click the "Shared access policies" blade of the queue
1. Click add, check at least "Send" and "Listen", and click "Create"
1. Click the created policy, copy out one of the "Connection String"

## Run the demo

1. Paste the "Connection String" to the static field "CONNECTION_STRING" in the `ServiceBusExample` class.
1. Comment `receive` in `main` method, run the application to enqueue messages with different sessions
1. Comment `send`, uncomment `recieve`. Run multiple application instances to see how they process the messages.
   * each application processes a given session, in parallel
   * the messages in each session is processed sequentially