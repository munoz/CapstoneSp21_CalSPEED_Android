package com.cp_kotlin

import DB.AppDatabase
import DB.speedResultsdao
import Java.backend
import Java.funString
import Java.speedResults
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionSet
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
    private var mMainScene: Scene? = null
    private var mStartScene: Scene? = null
    private var mResScene: Scene? = null
    private var mTestScene: Scene? = null
    private var mSceneRoot: ViewGroup? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vew = inflater.inflate(R.layout.fragment_1,container,false)
        assert(vew != null)
        mSceneRoot = vew.findViewById(R.id.container)
        var otherroot: ViewGroup? = mSceneRoot
        // BEGIN_INCLUDE(instantiation_from_view)
        // A Scene can be instantiated from a live view hierarchy.
        //mStartScene = Scene.getSceneForLayout(mSceneRoot,R.layout.fragment_1,activity)
        //mMainScene = Scene(mSceneRoot, otherroot.findViewById<View>(R.id.container) as ViewGroup)
        //mMainScene = Scene(mSceneRoot,mSceneRoot!!.findViewById(R.id.container))
        mMainScene = Scene.getSceneForLayout(mSceneRoot,R.layout.fragment_1,activity)
        mTestScene = Scene.getSceneForLayout(mSceneRoot,R.layout.testfrag,activity)
        mResScene = Scene.getSceneForLayout(mSceneRoot,R.layout.resultfrag,activity)
        return vew
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TransitionManager.go(mMainScene)


        //progress.visibility = View.GONE
        //resultsLayout.visibility = View.GONE
        mSpeedResults =
                this.context?.let {
                    Room.databaseBuilder(it, AppDatabase::class.java, AppDatabase.dbName)
                            .allowMainThreadQueries()
                            .build()
                            .getspeedResultsdao()
                }
        //var up: Button? = button_incr

        button_incr!!.setOnClickListener {
            //goToScene(mTestScene)
            //reaquire()
            updateProgressBar()
        }
        //var rst: Button? = restart
        restart!!.setOnClickListener { updateProgressBar() }

    }
    private fun reload(){
        var currentFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.view_pager)

        if (currentFragment is Fragment1) {
            var fragTransaction =   requireActivity().supportFragmentManager.beginTransaction()
            fragTransaction.detach(currentFragment as Fragment)
            fragTransaction.attach(currentFragment)
            fragTransaction.commit()
        }
        reaquire()
    }
    private fun reaquire(){

        prog_ping = progress_bar_ping
        prog_down = progress_bar_down
        prog_up = progress_bar_up
        curTest = text_view_progress
    }
    private fun goToScene(scene: Scene?) {
        val changeBounds = ChangeBounds()
        changeBounds.duration = 1000
        val fadeOut = Fade(Fade.OUT)
        fadeOut.duration = 1000
        val fadeIn = Fade(Fade.IN)
        fadeIn.duration = 1000
        val transition: TransitionSet = TransitionSet()
        transition!!.ordering = TransitionSet.ORDERING_SEQUENTIAL
        transition
            .addTransition(fadeOut)
            .addTransition(changeBounds)
            .addTransition(fadeIn)

        val auto: Transition? = AutoTransition()
        TransitionManager.go(scene, auto)
        Handler().postDelayed(
                {
                    // This method will be executed once the timer is over
                },
                1000 // value in milliseconds
        )

    }
    private fun updateProgressBar() {
        val handler = Handler()

        //TransitionManager.go(mTestScene)
        //goToScene(mTestScene)
//        prog_ping = null
//        prog_down = null
//        prog_up = null
//        curTest = null
        //reaquire()
        if(resultsLayout.visibility == View.VISIBLE){
            resultsLayout.visibility = View.GONE
        }
        if (restart.visibility == View.VISIBLE){
            restart.visibility =View.GONE
        }
        if(button_incr.visibility == View.VISIBLE){
            button_incr.visibility = View.INVISIBLE
        }
        if(animationView1.visibility == View.INVISIBLE){
            animationView1.visibility = View.VISIBLE
        }
        title.visibility = View.INVISIBLE
        test.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        reaquire()
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
        val maxd = nRand.nextInt(3000) + 5
        val startSpeed = nRand.nextInt(maxd / 2)

        val t = Thread(Runnable {
            ///////////////////////////////////////DOWNLOAD//////////////////////////////////
            handler.post { test!!.text = "Download" }
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
                        curTest!!.text = (currsped/ 100.00).toString() + "Mb/s"
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
            DownSpeed = backend.Speed(overall) / 100.00
            Log.d("Down Speed", "" + DownSpeed)
            Log.d("Last speed ", "" + prevsped)
            thistest.setDown(DownSpeed)
            thistest.setStream(backend.Stream(DownSpeed))
            ///////////////////////////////////////////UPLOAD////////////////////////////////////////
            handler.post { test!!.text = "Upload" }
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
                        curTest!!.text = (curspeed / 100.00).toString() + "Mb/s"
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
            upSpeed = backend.Speed(overall) / 100.00
            val prnt = "" + upSpeed
            Log.d("Up speed", prnt)
            thistest.setUp(upSpeed)
            thistest.setConf(backend.Conference(DownSpeed, upSpeed))
            /////////////////////////////////////////////PING//////////////////////////////////
            Log.d("Test", "In SystemCommand")
            val runtime = Runtime.getRuntime()
            try {
                Log.d("Test", "Trying Ping")
                handler.post { test!!.text = "Ping" }
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
                val `is` = p.inputStream
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
                                    handler.post { curTest!!.text = arrofs[6].substring(5)+"ms" }
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

                //prog_ping = progress_bar_ping
                //prog_down = progress_bar_down
                //prog_up = progress_bar_up
                //curTest = text_view_progress
                handler.post {
                    //goToScene(mResScene)
                    //reaquire()
                    text_view_progress.text = "Done"
                    //prog_ping!!.progress = 100
                    //prog_down!!.progress = 100
                    //prog_up!!.progress = 100
                    //curTest!!.text = "Done"
                    textView2.text = "Ping      $pings"+"ms"
                    textView3.text = "Down     $downs"+"mb/s"
                    textView4.text = "Up         $ups"+"mb/s"

                    progress!!.visibility = View.GONE
                    animationView1!!.visibility = View.INVISIBLE
                    resultsLayout!!.visibility= View.VISIBLE
                    restart!!.visibility=View.VISIBLE
                    test!!.visibility = View.INVISIBLE


                }


            } catch (e: Exception) {
                //Log.d("Test","Error");
                e.printStackTrace()
            }
        })

        t.start()

    }
}