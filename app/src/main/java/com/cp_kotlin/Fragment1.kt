package com.cp_kotlin

import DB.AppDatabase
import DB.speedResultsdao
import Java.backend
import Java.funString
import Java.speedResults
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_1.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.util.*

class Fragment1 : Fragment() {
    var mSpeedResults: speedResultsdao? = null
    var prog_ping: ProgressBar? = null
    var prog_down: ProgressBar? = null
    var prog_up: ProgressBar? = null
    var curTest: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prog_ping = progress_bar_ping
        prog_down = progress_bar_down
        prog_up = progress_bar_up
        curTest = text_view_progress
        progress_bar_ping.visibility = View.INVISIBLE
        progress_bar_down.visibility = View.INVISIBLE
        progress_bar_up.visibility = View.INVISIBLE
        resultsLayout.visibility= View.INVISIBLE
        mSpeedResults =
            this.context?.let {
                Room.databaseBuilder(it, AppDatabase::class.java, AppDatabase.dbName)
                    .allowMainThreadQueries()
                    .build()
                    .getspeedResultsdao()
            }
        var up: Button? = button_incr
        up!!.setOnClickListener { updateProgressBar() }
    }

    private fun updateProgressBar() {
        button_incr.visibility = View.INVISIBLE
        progress_bar_ping.visibility = View.VISIBLE
        progress_bar_down.visibility = View.VISIBLE
        progress_bar_up.visibility = View.VISIBLE
        prog_up!!.progress = 0
        prog_down!!.progress = 0
        prog_ping!!.progress = 0
        val thistest = speedResults()
        thistest.network = backend.getNetworkClass(context)
        val lat = 36.6517
        val longi = 121.7978
        thistest.setLatitude(lat.toString())
        thistest.setLongitude(longi.toString())

        val size = 100
        val packetSize = 1
        val timetaken: Queue<Int> = LinkedList()
        val amount = size / packetSize
        val nRand = Random()

        val maxd = nRand.nextInt(100) + 5
        val startSpeed = nRand.nextInt(maxd / 2)
        val handler = Handler()
        val t = Thread(Runnable {
            ///////////////////////////////////////DOWNLOAD//////////////////////////////////
            handler.post { curTest!!.text = "Download" }
            val DownSpeed: Double
            val spd = "" + startSpeed
            Log.d("start d speed", spd)
            timetaken.add(startSpeed)
            prog_up!!.progress = prog_up!!.progress + 1
            var prevsped = startSpeed
            for (i in 0 until amount - 1) {
                val currsped: Int = backend.randomInt(prevsped, maxd)
                timetaken.add(currsped)
                prevsped = currsped
                //update progress here after certain amount
                try {
                    handler.post {
                        curTest!!.text = currsped.toString()
                        prog_up!!.progress = prog_up!!.progress + 1
                    }
                    if (timetaken.size >= 5) {
                        timetaken.remove()
                        val hold = timetaken.size
                        //                            Log.d("timetaken size",hold.toString());
                        //                            System.out.println("Whats in timetaken?");
                        //                            for(int j = 0; j < hold; j++){
                        //                                System.out.print(timetaken.toArray()[j]+" ");
                        //                            }
                    }
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            var overall = ArrayList(timetaken)
            DownSpeed = backend.Speed(overall)
            Log.d("Down Speed", "" + DownSpeed)
            Log.d("Last speed ", "" + prevsped)
            thistest.setDown(DownSpeed)
            thistest.setStream(backend.Stream(DownSpeed))
            ///////////////////////////////////////////UPLOAD////////////////////////////////////////
            handler.post { curTest!!.text = "Upload" }
            timetaken.clear()
            val maxup = nRand.nextInt(maxd - 2) + 2
            Log.d("Max up", "" + maxup)
            val startuSpeed = 0
            val uspd = "" + startuSpeed
            timetaken.add(startuSpeed)
            prog_down!!.progress = prog_down!!.progress + 1
            Log.d("Start u speed", uspd)
            val upSpeed: Double
            val st = System.currentTimeMillis().toInt()
            var prevspeed = startSpeed
            for (i in 0 until amount - 1) {
                val curspeed: Int = backend.randomInt(prevspeed, maxup)
                timetaken.add(curspeed)
                prevspeed = curspeed
                val tm = System.currentTimeMillis().toInt()
                val actme = tm - st
                val tme = "" + actme
                //Log.d("Up time",tme);
                try {
                    handler.post {
                        curTest!!.text = curspeed.toString()
                        prog_down!!.progress = prog_down!!.progress + 1
                    }
                    if (timetaken.size >= 5) {
                        val remove = timetaken.remove()
                        val hold = timetaken.size
                        //Log.d("timetaken size",hold.toString());
                    }
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            overall = ArrayList(timetaken)
            upSpeed = backend.Speed(overall)
            val prnt = "" + upSpeed
            Log.d("Up speed", prnt)
            thistest.setUp(upSpeed)
            thistest.setConf(backend.Conference(DownSpeed, upSpeed))
            /////////////////////////////////////////////PING//////////////////////////////////
            Log.d("Test", "In SystemCommand")
            val runtime = Runtime.getRuntime()
            try {
                Log.d("Test", "Trying Ping")
                handler.post { curTest!!.text = "Ping" }
                val p = runtime.exec("ping 127.0.0.1")
                var pid = -1
                try {
                    val f = p.javaClass.getDeclaredField("pid")
                    f.isAccessible = true
                    pid = f.getInt(p)
                    f.isAccessible = false
                } catch (e: Throwable) {
                    pid = -1
                }
                val `inst` = p.inputStream
                // reading output stream of the command
                val conv = pid
                Log.d("Process id", conv.toString())
                val pID = pid
                val result = funString()
                val packets = funString()
                val t = Thread(Runnable {
                    result.SetValue("")
                    val st = System.currentTimeMillis().toInt()
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, -1)
                    val yesterday = calendar.timeInMillis
                    val uniqueId = (st).toInt()
                    Log.d("Test", "start time: $uniqueId")
                    Log.d("Test", "Reading Ping")
                    val inputStream = BufferedReader(
                        InputStreamReader(p.inputStream)
                    )
                    var s: String? = ""
                    var time: Int
                    var yester = yesterday
                    try {
                        Log.d("first line", "" + inputStream.readLine())
                        while (inputStream.readLine().also { s = it } != null) {
                            Log.d("pinging", s!!)
                            //get time put into arraylist
                            //progresss ping progressbar every second plus 8
                            Log.d("Time test!", (System.currentTimeMillis().toInt()-uniqueId).toString())
                            Log.d("Time tested!",((yesterday + System.currentTimeMillis())  -uniqueId).toString())
                            if (((System.currentTimeMillis()).toInt() - uniqueId).also {
                                    time = it
                                } >= 1000) {
                                val arrofs =
                                    s!!.split(" ".toRegex()).toTypedArray()
                                if (arrofs[0] == "64") {
                                    Log.d(
                                        "ping output",
                                        arrofs[6].substring(5)
                                    )
                                    yester += (yester + System.currentTimeMillis()).toInt() - uniqueId.toLong()
                                    handler.post { prog_ping!!.progress = prog_ping!!.progress + 8 }
                                }
                                if (arrofs.size > 3) {
                                    if (arrofs[1] == "packets") {

                                        packets.SetValue(s)
                                        Log.d("packets",packets.toString())
                                    }
                                }
                            }
                            if (((System.currentTimeMillis()).toInt()  -uniqueId) >= 10000){
                                //Log.d("Ping","Time: "+(time-st));
                                if (pID != -1) {
                                    result.SetValue(s)
                                    Log.d(
                                        "percent ping",
                                        "" + prog_ping!!.progress
                                    )
                                    runtime.exec("kill -SIGINT $pID")
                                } else {
                                    p.destroy()
                                }
                            }
                        }
                        if (s == null) {
                            Log.d("Test", "Input stream Null")
                        }
                        Log.d("test", result.GetValue())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (p.isAlive) {
                        p.destroy()
                    }
                })
                t.start()
                p.waitFor()
                t.join()
                Log.d("Result?", result.GetValue())
                Log.d("Test", "Outside Loop")
                Log.d("final perc", "" + prog_ping!!.progress)
                p.destroy()
                val parsePing: Array<String> = result.GetValue().split("/").toTypedArray()
                val pbase = nRand.nextInt(140) + 40
                val ping = parsePing[4].toDouble()
                val jitter =
                    parsePing[6].substring(0, parsePing[6].length - 3).toDouble()
                //ping *=10;
                val jbase = nRand.nextInt(10)
                //jitter *= 10;
                DecimalFormat("#.##").format(ping)
                DecimalFormat("#.##").format(jitter)
                Log.d("jitter before", "" + jitter)
                Log.d("latency before", "" + ping)
                //ping += pbase;
                //jitter+= jbase;
                Log.d("jitter", "" + jitter)
                Log.d("latency", "" + ping)
                thistest.setJitter(jitter.toString())
                thistest.setPing(ping.toString())
                val pktls: String = packets.GetValue()
                val fndpkt =
                    pktls.split(" ".toRegex()).toTypedArray()
                val loss = fndpkt[5].substring(0, fndpkt[5].length - 1)
                val los = loss.toDouble()
                thistest.setMOS(backend.MOS(los, jitter, ping))
                thistest.setVoip(backend.VOIP(thistest.getMOS()))
                println("This test:\n$thistest")
                mSpeedResults!!.insert(thistest)
                val mspeeds = mSpeedResults!!.speedResults
                for (sped in mspeeds) {
                    Log.d("from room", "" + sped)
                }
                handler.post { prog_ping!!.progress = 100 }
                System.out.println(thistest)
                var pings: String? = thistest.ping
                var downs: Double? = thistest.down
                var ups: Double? = thistest.up

                handler.post {
                    curTest!!.visibility = View.INVISIBLE
                    textView2.text = "Ping         $pings"+"ms"
                    textView3.text = "Download $downs"+"mb/s"
                    textView4.text = "Upload   $ups"+"mb/s"
                    progress_bar_ping!!.visibility = View.INVISIBLE
                    progress_bar_down!!.visibility = View.INVISIBLE
                    progress_bar_up!!.visibility = View.INVISIBLE
                    resultsLayout!!.visibility= View.VISIBLE
                }


            } catch (e: Exception) {
                //Log.d("Test","Error");
                e.printStackTrace()
            }
        })
        //Download

        //Download
        var DownSpeed: Double
        t.start()

    }
}