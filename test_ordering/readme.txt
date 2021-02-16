执行jar包的命令如下：
java -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-RestrictContended -jar jcstress-learning.jar -v -t ConcurrencyTest

没加上volatile之前，0结果出现的次数为3,504次
  Observed state   Occurrences              Expectation  Interpretation
               0         3,504   ACCEPTABLE_INTERESTING  !!!!
               1    58,397,548               ACCEPTABLE  ok
               4    92,588,469               ACCEPTABLE  ok


加上volatile之后，0结果出现的次数为0次
  Observed state   Occurrences              Expectation  Interpretation
               0             0   ACCEPTABLE_INTERESTING  !!!!
               1       327,671               ACCEPTABLE  ok
               4             0               ACCEPTABLE  ok
