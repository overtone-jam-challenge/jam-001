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

(def metro (metronome 100))

(defn bass-player [beat notes vels durs]
  (at (metro beat)
      (bass (first notes))
            (first vels)
            (first durs))

  (apply-at #'bass-player
            (metro (inc beat))
            (inc beat)
            (next notes)
            (next vels)
            (next durs)))

(def b-notes [40 40 43 45])
(def b-vels  [0.4 0.3 0.3 0.4])
(def b-durs  [0.5 0.1 0.4 0.5])

(bass-player (metro)
             (cycle b-notes)
             (cycle b-vels)
             (cycle b-durs))


