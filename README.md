# solace-msg-sender
ssh to solace CLI :
ssh admin@192.168.3.73 -p 2222

ULR of Soalce Management Dashboard:
http://192.168.3.73:8080/#/login

Reference of solace SEMP:
http://192.168.3.73:8080/SEMP/v2/config/help/#/

create queue testQ2 in vpn default with admin:admin
curl http://192.168.3.73:8080/SEMP/v2/config/msgVpns/default/queues -X POST -u admin:admin -H "Content-Type: application/json" -d '{"queueName":"testQ2","accessType":"exclusive","maxMsgSpoolUsage":200,"permission":"consume","ingressEnabled":true,"egressEnabled":true}'

create subscription on testQ2
curl http://192.168.3.73:8080/SEMP/v2/config/msgVpns/default/queues/testQ2/subscriptions -X POST -u admin:admin -H "Content-Type: application/json" -d '{"msgVpnName": "default","queueName": "testQ2","subscriptionTopic": "greet/>"}'

show queues in vpn default:
http://192.168.3.73:8080/SEMP/v2/config/msgVpns/default/queues

