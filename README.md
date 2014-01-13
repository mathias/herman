# herman & its thundering (hadoop) herd

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Woody_Herman.jpg/220px-Woody_Herman.jpg" alt="Woody Herman playing clarinet" align="right">

This project is a combination of Pallet recipes to create a Hadoop cluster (or a single Hadoop node locally with Virtualbox) and Cascalog to write queries on them.

The name is in reference to Woody Herman and His Thundering Herd big band. Herman was a Jazz clarinetist originally from my hometown of Milwaukee, WI.

> Woodrow Charles "Woody" Herman (May 16, 1913 – October 29, 1987) was an American jazz clarinetist, alto and soprano saxophonist, singer, and big band leader. Leading various groups called "The Herd," Herman was one of the most popular of the 1930s and 1940s bandleaders.

&mdash; [Woody Herman - Wikipedia, the free encyclopedia](https://en.wikipedia.org/wiki/Woody_Herman)

## Usage

Don't yet. This isn't ready for prime time.

### Temporary Vagrant setup:

For local dev, I quickly set up a Virtualbox VM with Vagrant, using the following instructions. (Couldn't get Pallet to work yet, but eventually both dev and prod boxes will be provisioned and configured with Pallet.)

```sh
vagrant up
vagrant vbguest --do install
vagrant ssh
```

Now we set up Hadoop on the VM itself:

```sh
sudo apt-get update
sudo aptitude install openjdk-7-jdk
echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/" >> ~/.bashrc

mkdir hadoop-install
cd hadoop-install

wget http://archive.cloudera.com/cdh/3/hadoop-latest.tar.gz
tar xvzf hadoop-latest.tar.gz
cd hadoop-<version>

vi conf/hadoop-env.sh
# Edit the JAVA_HOME line as above.
```

Then edit `conf/core-site.xml` and replace the contents with:

```xml
<configuration>
     <property>
         <name>fs.default.name</name>
         <value>hdfs://localhost:9000</value>
     </property>
</configuration>
```

Edit `conf/hdfs-site.xml` and replace the contents with:

```xml
<configuration>
     <property>
         <name>dfs.replication</name>
         <value>1</value>
     </property>
</configuration>
```

Edit `conf/mapred-site.xml`

```xml
<configuration>
     <property>
         <name>mapred.job.tracker</name>
         <value>localhost:9001</value>
     </property>
</configuration>
```

Old:
```
wget http://www.poolsaboveground.com/apache/hadoop/core/stable/hadoop-2.2.0.tar.gz
# this is a mirror of the latest stable release of Hadoop. You'll probably want to go get a newer version from (potentially) a different mirror in the future..

tar xvzf hadoop-2.2.0.tar.gz
ln -s `pwd`/hadoop-2.2.0 hadoop
echo "export HADOOP_INSTALL=`pwd`" >> ~/.bashrc

# re-source exported vars
source ~/.bashrc

cd hadoop-2.2.0

# Start the minicluster (one node)

bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-2.2.0-tests.jar minicluster
```


## License

Copyright © 2013 Matt Gauger

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
