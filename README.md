Insertion interleaving test
===========================

This repository contains tests for a particular edge case that occurs in collaborative text editing CRDTs.
The tests consist of two replicas, both of which initially start with the two-character string:

    <>

On one replica, the string is edited by inserting a sequence of hyphens between the two angle brackets:

    <---------->

At the same time, on the other replica, a sequence of hash signs are inserted in the same place:

    <##########>

Finally, the two replicas merge, and we inspect the outcome.
In some editing algorithms, the merged result is always either `<----------##########>` or
`<##########---------->`. However, in some other algorithms the insertions may be interleaved,
resulting in a final string such as `<#---##--###--##---##>`.

This interleaving behaviour can be problematic. Imagine two users each inserted several paragraphs
of text at the same place in a document. If the merged result is a character-by-character random
interleaving of those paragraphs, the outcome is likely to be an unreadable jumble of letters, which
is probably not what the users wanted.


Running the tests
-----------------

This repository contains [Java implementations](https://github.com/PascalUrso/ReplicationBenchmark)
of several collaborative editing algorithms by Ahmed-Nacer et al [1]. The included algorithms are
Logoot [9, 10], RGA [6], SOCT2 [7], WOOT [4], WOOTO [8], and WOOTH [1].

To compile the Java algorithms and execute the interleaving tests, install
[Maven](https://maven.apache.org/), and then you can run:

    $ mvn compile exec:java -Dexec.mainClass=jbenchmarker.Interleaving

Moreover, this repository contains a JavaScript test for the
[implementation of the LSEQ algorithm](https://github.com/Chat-Wane/LSEQTree) by Nédelec et al [2, 3].
The code of this implementation is [hosted on npm](https://www.npmjs.com/package/lseqtree).
To download the code and run the test, install [Node.js](https://nodejs.org/), and then run:

    $ npm install
    $ node lseq-test.js

The current tests observe the interleaving anomaly in Logoot, SOCT2, and LSEQ. They do not observe
interleaving in RGA, WOOT, WOOTO, or WOOTH. Obviously, the test cannot prove that interleaving will
never occur in those algorithms.


References
----------

The Java implementations of Logoot, RGA, SOCT2, WOOT, WOOTO, and WOOTH were developed by
Mehdi Ahmed-Nacer, Gérald Oster, Pascal Urso (judging by the @author tags in the code), and released
at https://github.com/PascalUrso/ReplicationBenchmark under the
[GPLv3 license](https://opensource.org/licenses/GPL-3.0).

The JavaScript implementation of LSEQ was developed by "Chat-Wane" (presumably Brice Nédelec?), and
released at https://github.com/Chat-Wane/LSEQTree under the
[MIT license](https://opensource.org/licenses/MIT).

The interleaving tests (`lseq-test.js` and `src/jbenchmarker/Interleaving.java`) and this README
were developed by Martin Kleppmann, and released under the
[GPLv3 license](https://opensource.org/licenses/GPL-3.0) for license compatibility with the above.

Literature references:

1. Mehdi Ahmed-Nacer, Claudia-Lavinia Ignat, Gérald Oster, Hyun-Gul Roh, and Pascal Urso:
  “[Evaluating CRDTs for real-time document editing](https://hal.inria.fr/docs/00/62/95/03/PDF/doce63-ahmednacer.pdf),”
  at *11th ACM Symposium on Document Engineering* (DocEng), pages 103–112, September 2011.
  [doi:10.1145/2034691.2034717](http://dx.doi.org/10.1145/2034691.2034717)

2. Brice Nédelec, Pascal Molli, Achour Mostefaoui, and Emmanuel Desmontils:
  “[LSEQ: an Adaptive Structure for Sequences in Distributed Collaborative Editing](https://hal.archives-ouvertes.fr/file/index/docid/921633/filename/fp025-nedelec.pdf),”
  at *13th ACM Symposium on Document Engineering* (DocEng), pages 37–46, September 2013.
  [doi:10.1145/2494266.2494278](http://dx.doi.org/10.1145/2494266.2494278)

3. Brice Nédelec, Pascal Molli, and Achour Mostefaoui:
  “[CRATE: Writing Stories Together with our Browsers](https://hal.archives-ouvertes.fr/hal-01303333),”
  at *25th International World Wide Web Conference* (WWW), pages 231–234, April 2016.
  [doi:10.1145/2872518.2890539](http://dx.doi.org/10.1145/2872518.2890539)

4. Gérald Oster, Pascal Urso, Pascal Molli, and Abdessamad Imine:
  “[Data consistency for P2P collaborative editing](https://hal.archives-ouvertes.fr/file/index/docid/108523/filename/OsterCSCW06.pdf),”
  at *ACM Conference on Computer Supported Cooperative Work* (CSCW), pages 259–268, November 2006.
  [doi:10.1145/1180875.1180916](http://dx.doi.org/10.1145/1180875.1180916)

5. Nuno Preguiça, Joan Manuel Marques, Marc Shapiro, and Mihai Letia:
  “[A Commutative Replicated Data Type for Cooperative Editing](https://hal.inria.fr/inria-00445975/document),”
  at *29th IEEE International Conference on Distributed Computing Systems* (ICDCS), pages 395–403, June 2009.
  [doi:10.1109/ICDCS.2009.20](http://dx.doi.org/10.1109/ICDCS.2009.20)

6. Hyun-Gul Roh, Myeongjae Jeon, Jin-Soo Kim, and Joonwon Lee:
  “[Replicated abstract data types: Building blocks for collaborative applications](http://csl.skku.edu/papers/jpdc11.pdf),”
  *Journal of Parallel and Distributed Computing*, volume 71, number 3, pages 354–368, March 2011.
  [doi:10.1016/j.jpdc.2010.12.006](http://dx.doi.org/10.1016/j.jpdc.2010.12.006)

7. Maher Suleiman, Michèle Cart, and Jean Ferrié:
  “Serialization of concurrent operations in a distributed collaborative environment,”
  at *International Conference on Supporting Group Work* (GROUP), pages 435–445, November 1997.
  [doi:10.1145/266838.267369](http://dx.doi.org/10.1145/266838.267369)

8. Stéphane Weiss, Pascal Urso, and Pascal Molli:
  “[Wooki: A P2P Wiki-Based Collaborative Writing Tool](http://pagesperso.lina.univ-nantes.fr/~molli-p/pmwiki/uploads/Main/weiss07.pdf),”
  at *8th International Conference on Web Information Systems Engineering* (WISE), pages 503–512, December 2007.
  [doi:10.1007/978-3-540-76993-4_42](http://dx.doi.org/10.1007/978-3-540-76993-4_42)

9. Stéphane Weiss, Pascal Urso, and Pascal Molli:
  “[Logoot: A Scalable Optimistic Replication Algorithm for Collaborative Editing on P2P Networks](http://pagesperso.lina.univ-nantes.fr/~molli-p/pmwiki/uploads/Main/weiss09.pdf),”
  at *29th IEEE International Conference on Distributed Computing Systems* (ICDCS), pages 404–412, June 2009.
  [doi:10.1109/ICDCS.2009.75](http://dx.doi.org/10.1109/ICDCS.2009.75)

10. Stéphane Weiss, Pascal Urso, and Pascal Molli:
  “[Logoot-Undo: Distributed Collaborative Editing System on P2P Networks](https://www.researchgate.net/profile/Pascal_Urso/publication/233882440_Logoot-Undo_Distributed_Collaborative_Editing_System/links/0fcfd50c84f5194937000000.pdf),”
  *IEEE Transactions on Parallel and Distributed Systems*, volume 21, number 8, pages 1162–1174, January 2010.
  [doi:10.1109/TPDS.2009.173](http://dx.doi.org/10.1109/TPDS.2009.173)
