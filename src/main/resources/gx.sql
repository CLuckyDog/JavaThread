select a.subscriptionID,a.spid,a.orderID,a.orderFlag,a.itemID,a.oa,a.oaUserFlag,a.da,a.daUserFlag,
a.fa,a.faType,a.status,a.subscribeTime,a.effectiveTime,a.expireTime,a.suspendReason,
a.suspendTime,a.resumeTime,'',a.withdrawTime,a.isSubscription,0
from I_SUBSCRIBEINFO00 a,I_PACKAGEINFO b
 where a.orderID = b.packageID and b.packageFlag != 0 and a.isSubscription=1 and a.status=0 and a.orderFlag = 0
and a.subscribeTime<'2021-02-25';

select * from I_ISSUBSCRIBE0100;

 select a.serno,a.subscriptionID,a.cdrtype,a.spid,
a.orderID,a.orderName,a.orderFlag,a.itemID,a.itemName,a.loginAccount,a.userFlag,a.fa,
a.faType,d.baseFee,d.baseFee,a.subscribeTime,a.orderNo,a.cityCode,a.orderType,a.isDealdet
from  I_USERDET02 a,I_ISSUBSCRIBE0200 b,I_PRODUCTINFO c,I_CHARGING d
where a.subscriptionID = b.subscriptionID and a.loginAccount = b.da
and a.realFee >= 0 and c.productID = a.orderID and c.chargingPolicyID = d.chargingPolicyID and a.orderType !=8
and a.orderType !=9 and a.orderType !=10 ;

select a.serno,a.subscriptionID,a.cdrtype,a.spid,
a.orderID,a.orderName,a.orderFlag,a.itemID,a.itemName,a.loginAccount,a.userFlag,a.fa,
a.faType,d.baseFee,d.baseFee,a.subscribeTime,a.orderNo,a.cityCode,a.orderType,a.isDealdet
from I_USERDET02 a,I_ISSUBSCRIBE0200 b,I_PACKAGEINFO c,I_CHARGING d
where a.subscriptionID = b.subscriptionID and a.loginAccount = b.da
and a.realFee >= 0 and c.packageID = a.orderID and c.chargingPolicyID = d.chargingPolicyID and c.packageFlag != 0
and a.orderType !=8 and a.orderType !=9 and a.orderType !=10 ;