(ns jam-challenge-001.core
    (:use overtone.live))

(definst bass [note 58 vel 0.4 dur 0.2]
  (let [freq (midicps note)
        env (env-gen (perc 0.1 dur) 1 1 0 1 :free)
        src (saw [freq (* 1.02 freq)])
        sub (sin-osc (/ freq 2))
        filt (rlpf src (* 1.1 freq) 0.8)]
    (* vel
       env
       (+ src sub))))

;(bass)

(def metro (metronome 400))
; (metro :bpm 600)

(def myscale (vector 44 46 47 49 51 52 55 56))
; redefine myscale while logistic-seq is running to change the notes used

(def r 3.9)
; redefine r to change the character of the chaos. Outside the range 3.53 < r < 4 
; you will not get chaos. You may get alternation between 2 vals or a constant value
;

(defn logistic-seq [ beat x ]
  ; Logistic sequence generates chaos using the formula
  ; x(n+1)=x(n)r(1-x(n))
  ; 3.53 < r < 4 will cause chaos
 (let [ new-x (* r x (- 1 x))
       ; next line converts x (which is 0 < x < 1) into notes
       note (nth myscale (int (* (count myscale) x)))]
   (at (metro beat) (bass note))
   (apply-at #'logistic-seq (metro (inc beat))
                            [(inc beat) new-x] )))

(logistic-seq (metro) 0.5 )
