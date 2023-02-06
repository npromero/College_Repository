﻿using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Security.Cryptography;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public float speed;                     // Speed in units/second
    public GameObject destination;          // Current point enemy is moving towards in world space
    public GameObject nextDestination;      // Next point enemy will move to. Assigned when new waypoint area is entered, and set to destination when previous area is left
    public bool isProtected;                // If true, enemy is invincible due to spawn protection, or it is attacking a cow
    public float health;
    public int moneyDropped;
    private float slowDuration;
    private float splashMinRange;
    private float splashMaxRange;
    private float lineWidth;
    private float lineDuration;
    private float towerDamage;
    private float cascadeDecrease;
    private int cascadeTimes;
    private int cascadeCount;
    private float cascadeRangeMin;
    private float cascadeRangeMax;
    private float slowDecrease;
    public Laser laser;
    private float damagePerUpdate;

    // slow tower settings
    private Color baseColor = new Color(255, 255, 255, 255);        // normal color
    private Color slowedColor = new Color(0, 150, 255, 200);        // blue tint
    private SlowTimer slowTimer;                                    // timer that controls enemy speed
    private float minSpeed;                                         // min speed this enemy can move
    private float initialSpeed;                                     // initial speed of this enemy
    private bool slowed;                                            // flag for being slowed

    //fire damage
    private FireTimer firetimer;
    private bool onfire;
    private SpriteRenderer fireimage;
    public GameObject fire;

    // Start is called before the first frame update
    void Awake() {
        destination = null;
        nextDestination = destination;

        isProtected = true;
        slowTimer = gameObject.AddComponent<SlowTimer>();
        slowed = false;
        initialSpeed = speed;
        minSpeed = initialSpeed * 0.10f;

        onfire = false;
        firetimer = gameObject.AddComponent<FireTimer>();
        fireimage = fire.GetComponent<SpriteRenderer>();

        // increases enemy hp by 10% every wave
        health *= (1 + ((Level.WaveNumber-1) * .1f));
    }

    // called once per frame
    void FixedUpdate()
    {
        // unslows enemy if timer expired
        if (slowed && !slowTimer.enabled) {
            UnSlow();
        }

        //Damages if on fire
        if(onfire && !firetimer.enabled)
        {
            Douse();
        }
        else if(onfire)
        {
            Hit(damagePerUpdate);
        }

        // Move towards destination
        transform.position = Vector2.MoveTowards(transform.position, destination.transform.position, speed * Time.fixedDeltaTime);

        // enemy death
        if (health <= 0) {

            // briefly display coins at enemy's feet where they died
            if (Level.coinTextCount < Level.COIN_TEXT_LIMIT) {
                Level.coinTextList[Level.nextCoinIndex].StartFadeCycle(transform.position);
                Level.coinTextCount++;
                Level.nextCoinIndex++;
                if (Level.nextCoinIndex >= Level.COIN_TEXT_LIMIT)
                    Level.nextCoinIndex = 0;
            }

            Currency.addCurrency(Currency.scaleReward(moneyDropped));
            Level.EnemiesRemaining--;
            Destroy(gameObject);
        }
    }

    // enemy takes damage
    void Hit(float damage) {
        if (!isProtected)
            health -= damage;
    }

    // applies slow, adds to timer, colors enemy blue
    void Slow(float decrease) {

        slowed = true;
        float newSpeed = (speed * (1 - decrease));
        if (newSpeed >= minSpeed) {
            speed = newSpeed;
        }

        slowTimer.addDuration(4.0f);
        GetComponent<SpriteRenderer>().color = slowedColor;
    }

    // resets enemy speed / color
    void UnSlow() {
        speed = initialSpeed;
        GetComponent<SpriteRenderer>().color = baseColor;
        slowed = false;
    }

    void SetOnFire()
    {
        onfire = true;
        firetimer.addDuration(4.0f);
        fireimage.enabled = true;
    }

    void Douse()
    {
        onfire = false;
        fireimage.enabled = false;
    }
    
    void setSlowDuration(float duration) {
        slowDuration = duration;
    }

    void setMinRange(float min) {
        splashMinRange = min;
    }

    void setMaxRange(float max) {
        splashMaxRange = max;
    }

    void setLineWidth(float width) {
        lineWidth = width;
    }

    void setLineDuration(float duration) {
        lineDuration = duration;
    }

    void setTowerDamge(float damage) {
        towerDamage = damage;
    }

    void setCascadeDecrease(float decrease)
    {
        cascadeDecrease = decrease;
    }

    void setCascadeTimes(int times)
    {
        cascadeTimes = times;
    }

    void setCascadeCount(int count)
    {
        cascadeCount = count;
    }

    void setMinRangeCascade(float min)
    {
        cascadeRangeMin = min;
    }

    void setMaxRangeCascade(float max)
    {
        cascadeRangeMax = max;
    }

    void setDecrease(float decrease)
    {
        slowDecrease = decrease;
    }

    void setFireDamage(float damage)
    {
        damagePerUpdate = damage;
    }

    void Splash()
    {
        GameObject[] gos = GameObject.FindGameObjectsWithTag("Enemy");
        Vector3 position = this.transform.position;

        // calculate squared distances
        float min = splashMinRange * splashMinRange;
        float max = splashMaxRange * splashMaxRange;
        foreach (GameObject go in gos)
        {
            if (!(go.Equals(this.gameObject)))
            {
                Vector3 diff = go.transform.position - position;
                float curDistance = diff.sqrMagnitude;
                if (curDistance >= min && curDistance <= max)
                {
                    Laser drawLaser = Instantiate(laser);
                    drawLaser.SendMessage("setStartPosition", this.transform.position);
                    drawLaser.SendMessage("setEndPosition", go.transform.position);
                    drawLaser.SendMessage("setColor", Color.red);
                    drawLaser.SendMessage("setWidth", lineWidth);
                    drawLaser.SendMessage("setDuration", lineDuration);
                    drawLaser.SendMessage("draw");
                    go.SendMessage("Hit", towerDamage);
                }
            }
        }
    }

    void SplashIce()
    {
        GameObject[] gos = GameObject.FindGameObjectsWithTag("Enemy");
        Vector3 position = this.transform.position;

        // calculate squared distances
        float min = splashMinRange * splashMinRange;
        float max = splashMaxRange * splashMaxRange;
        foreach (GameObject go in gos)
        {
            if (!(go.Equals(this.gameObject)))
            {
                Vector3 diff = go.transform.position - position;
                float curDistance = diff.sqrMagnitude;
                if (curDistance >= min && curDistance <= max)
                {
                    Laser drawLaser = Instantiate(laser);
                    drawLaser.SendMessage("setStartPosition", this.transform.position);
                    drawLaser.SendMessage("setEndPosition", go.transform.position);
                    drawLaser.SendMessage("setColor", Color.cyan);
                    drawLaser.SendMessage("setWidth", lineWidth);
                    drawLaser.SendMessage("setDuration", lineDuration);
                    drawLaser.SendMessage("draw");
                    go.SendMessage("setSlowDuration", slowDuration);
                    go.SendMessage("Slow", slowDecrease);
                }
            }
        }
    }

    void SplashFire()
    {
        GameObject[] gos = GameObject.FindGameObjectsWithTag("Enemy");
        Vector3 position = this.transform.position;

        // calculate squared distances
        float min = splashMinRange * splashMinRange;
        float max = splashMaxRange * splashMaxRange;
        foreach (GameObject go in gos)
        {
            if (!(go.Equals(this.gameObject)))
            {
                Vector3 diff = go.transform.position - position;
                float curDistance = diff.sqrMagnitude;
                if (curDistance >= min && curDistance <= max)
                {
                    Laser drawLaser = Instantiate(laser);
                    drawLaser.SendMessage("setStartPosition", this.transform.position);
                    drawLaser.SendMessage("setEndPosition", go.transform.position);
                    drawLaser.SendMessage("setColor", Color.red);
                    drawLaser.SendMessage("setWidth", lineWidth);
                    drawLaser.SendMessage("setDuration", lineDuration);
                    drawLaser.SendMessage("draw");
                    go.SendMessage("setFireDamage", damagePerUpdate);
                    go.SendMessage("SetOnFire");
                }
            }
        }
    }

    void Cascade()
    {
        if (cascadeCount < cascadeTimes)
        {
            GameObject[] gos = GameObject.FindGameObjectsWithTag("Enemy");
            GameObject target = null;
            float distance = Mathf.Infinity;
            Vector3 position = this.transform.position;

            // calculate squared distances
            float min = cascadeRangeMin * cascadeRangeMin;
            float max = cascadeRangeMax * cascadeRangeMax;
            foreach (GameObject go in gos)
            {
                if (!(go.Equals(this.gameObject)))
                {
                    Vector3 diff = go.transform.position - position;
                    float curDistance = diff.sqrMagnitude;
                    if (curDistance < distance && curDistance >= min && curDistance <= max)
                    {
                        target = go;
                        distance = curDistance;
                    }
                }
            }

            if (target != null)
            {
                towerDamage = towerDamage * (1 - cascadeDecrease);
                cascadeCount++;
                Laser drawLaser = Instantiate(laser);
                drawLaser.SendMessage("setStartPosition", position);
                drawLaser.SendMessage("setEndPosition", target.transform.position);
                drawLaser.SendMessage("setColor", Color.yellow);
                drawLaser.SendMessage("setWidth", lineWidth);
                drawLaser.SendMessage("setDuration", lineDuration);
                drawLaser.SendMessage("draw");
                target.SendMessage("setCascadeDecrease", cascadeDecrease);
                target.SendMessage("setCascadeTimes", cascadeTimes);
                target.SendMessage("setLineWidth", lineWidth);
                target.SendMessage("setLineDuration", lineDuration);
                target.SendMessage("setTowerDamge", towerDamage);
                target.SendMessage("setMinRangeCascade", cascadeRangeMin);
                target.SendMessage("setMaxRangeCascade", cascadeRangeMax);
                target.SendMessage("setCascadeCount", cascadeCount);
                target.SendMessage("Cascade");
                target.SendMessage("Hit", towerDamage);
            }
        }
    }

    // timer for tracking duration remaining on slow
    class SlowTimer : MonoBehaviour {

        private float timeRemaining = 0.0f;     // time remaining
        private float maxDuration = 7.5f;       // maximum duration

        private void Awake() {
            this.enabled = false;
        }

        // adds time to the slow, cannot exceed max duration
        public void addDuration(float seconds) {
            if (! this.enabled)
                this.enabled = true;

            timeRemaining += seconds;
            if (timeRemaining > maxDuration) {
                timeRemaining = maxDuration;
            }
        }

        // updates timer, disables when reaching 0
        private void FixedUpdate() {
            if (timeRemaining >= 0) {
                timeRemaining -= Time.fixedDeltaTime;
            }
            else {
                this.enabled = false;
            }
        }
    }

    // timer for tracking duration remaining on fire damage
    class FireTimer : MonoBehaviour
    {

        private float timeRemaining = 0.0f;     // time remaining
        private float maxDuration = 7.5f;       // maximum duration

        private void Awake()
        {
            this.enabled = false;
        }

        // adds time to the slow, cannot exceed max duration
        public void addDuration(float seconds)
        {
            if (!this.enabled)
                this.enabled = true;

            timeRemaining += seconds;
            if (timeRemaining > maxDuration)
            {
                timeRemaining = maxDuration;
            }
        }

        // updates timer, disables when reaching 0
        private void FixedUpdate()
        {
            if (timeRemaining >= 0)
            {
                timeRemaining -= Time.fixedDeltaTime;
            }
            else
            {
                this.enabled = false;
            }
        }
    }
}
