;All search algorithms are in the "search" folder
;They can be imported like so
(require 'search.opsearch)
(require 'search.planner)
(require 'ourastarsearch)
(require 'plannertest)

;(A*search {:state 'a, :cost 0} (fn [x] (= x 'h)) a*lmg-map)
;(stateAdapter (:cmd (planner ss-one '(cas cargo-one station-two ) ops)))


(defn plan-route [planner-ss]
  (let [
        plannerout (plan planner-ss '(cas cargo-one station-two) ops)
        outputcmd (stateAdapter (apply str (:cmd plannerout)))
        ]

    (println (:txt plannerout))

    (loop [current outputcmd results [] po (:txt plannerout)]

      (if (empty? current)
        (println  "Planner Results are: " po " A* Results are: " results)
        (recur (rest current) (conj results (run-astar (first current))) po))
      )
    )
  )

(defn run-astar [goals]
  (let
    [
     stations (clojure.string/split goals #",")
     ]
    (println (first stations))
    (println (first (rest stations)))
    (A*search {:state (symbol (first stations)), :cost 0} (fn [x] (= x (symbol (first (rest stations))))) map1)
    )
  )

(defn temp [one two]
  (println (A*search {:state one, :cost 0} (fn [x] (= x two)) map1))
  )

(defn planner-test-all-ss []
  (println "Start-State-one"(:cmd (plan ss-one '(cas cargo-one station-two ) ops)))
  (println "Start-State-two" (:cmd (plan ss-two '(cas cargo-one station-two ) ops)))
  (println "Start-State-three" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-four" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-five" (:cmd (plan-multiple ss-five '((cas cargo-one station-four)(cas cargo-two station-two)) ops)))
  (println "Start-State-six" (:cmd (plan-multiple ss-six '((cas cargo-one station-two)(cas cargo-two station-five)) ops)))
  (println "Start-State-seven" (:cmd (plan ss-seven '(cas cargo-one station-one) ops)))
  (println "Start-State-eight" (:cmd (plan ss-eight '(cas cargo-one station-four) ops)))
  (println "Start-State-nine" (:cmd (plan ss-nine '(cas cargo-one station-eleven) ops)))
  )