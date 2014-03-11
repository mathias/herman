# herman & its thundering (hadoop) herd

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Woody_Herman.jpg/220px-Woody_Herman.jpg" alt="Woody Herman playing clarinet" align="right">

Does work for [remembrance](https://github.com/mathias/remembrance). The goal is to have this repo as an article ingestion worker queue and a method of running machine learning and NLP tasks on ingested articles.

The name is in reference to Woody Herman and His Thundering Herd big band. Herman was a Jazz clarinetist originally from my hometown of Milwaukee, WI.

> Woodrow Charles "Woody" Herman (May 16, 1913 – October 29, 1987) was an American jazz clarinetist, alto and soprano saxophonist, singer, and big band leader. Leading various groups called "The Herd," Herman was one of the most popular of the 1930s and 1940s bandleaders.

&mdash; [Woody Herman - Wikipedia, the free encyclopedia](https://en.wikipedia.org/wiki/Woody_Herman)

## Usage

Don't yet. This isn't ready for prime time.

Be sure to read up on Storm first:

http://storm-project.net/
https://github.com/nathanmarz/storm/
https://github.com/nathanmarz/storm/wiki

To run on a local cluster:

```bash
lein run -m herman.topology/run!
# OR
lein run -m herman.topology/run! debug false workers 10
```

To run on a distributed cluster:

```bash
lein uberjar
# copy jar to nimbus, and then on nimbus:
bin/storm jar path/to/uberjar.jar herman.TopologySubmitter workers 30 debug false
```

or use `[storm-deploy](https://github.com/nathanmarz/storm-deploy/wiki)`

## Thoughts on previous versions

I tried to use Pallet, Cascalog, and Hadoop to build a map reduce cluster, but it proved too difficult, and I'm not interesting in spending terribly much time on devops. Storm, on the other hand, works reasonably well now on my dev machine and can grow up to be a cluster when I need it to! That's not to say that Pallet and Cascalog aren't great -- they're wonderful! They just fill a different need than I have.

## License

Copyright © 2014 Matt Gauger

Distributed under the Eclipse Public License, the same as Clojure.
