rabbitTemplate 의 sendAndReceive는 내부적으로 다음과 같이 동작하는것으로 보인다

1. 임시 큐 생성 -> 요청한 클라이언트에게 메세지가 도착하도록 보장. replyTo에 세팅
2. correlationId 생성 -> 요청한 특정 클라이언트의 몇번째 요청문에 해당하는 메세지인지 매칭하기 위한 값. correlationId에 세팅
3. 고로 Message Property에 replyTo와 correlationId를 세팅해도 template 내부적으로 해당 값들을 다시 세팅해버린다 (덮어씌워짐)
4. Template은 내부적으로 correlationId(messageTag)를 key로 하는 replyHolder에 PendingReply 객체를 저장한다.
5. PendingReply 객체는 내부적으로 CompletableFuture를 갖고있다. (new CompletableFuture<Message>())
6. Template는 PendingReply내부의 future를 get하려고 시도한다(default wait time : 5000milsec)
7. 동시에 1번에서 생성된 임시 큐의 컨슈머가 replyTo 주소에 따라 되돌아온 데이터의 큐를 읽는다. replyHolder에서 해당 correlationId로 저장한 PendingReply를 찾고 PendingReply가 들고있는 completableFuture에 response 메세지 값을 넣어준다. (future.complete(message))
8. 6번에서 default값만큼 기다리던 future는 시간 안에 future의 결과가 들어올 경우 해당 값을 return한다 (blocking 끝)

위의 로직으로 client는 서버의 scale과 내부적 쓰레드의 갯수에 상관없이, 요청에 대한 쓰레드가 정확히 return 데이터를 받을 수 있는것이다.
API를 요청하듯 사용할 수 있게된다는 말이다.

event driven의 스펙을 잘 따르고있는것으로 보이므로.. (사실 이 방법 말고 더 좋은 방법이 있기나 한가... 왜 Message를 Sync하게 써야하는거야.. 이미 설계에서 부터 망한거지) 안심하고 
