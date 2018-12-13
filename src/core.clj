;All search algorithms are in the "search" folder
;They can be imported like so
(require 'search.opsearch)
(require 'search.planner)
(require 'ourastarsearch)
(require 'plannertest)

;(A*search {:state 'a, :cost 0} (fn [x] (= x 'h)) a*lmg-map)
;(stateAdapter (:cmd (planner ss-one '(cas cargo-one station-two ) ops)))


;(defn trainthing []
;  (let[
;       outputcmd (stateAdapter (apply str (:cmd(plan ss-one '(cas cargo-one station-two ) ops))))
;       ]

;    (loop[x 0, in outputcmd]
 ;     (
;        (def start (nth (clojure.string/split (str in) #",") 0))
  ;      ))


 ;   (def move (nth (clojure.string/split start #"move") (- x 1)))
 ;   (def startState (nth (clojure.string/split move #" ") 3))
;    (def goalState (nth (clojure.string/split move #" ") 2))
 ;   (def startState (apply str startState))
 ;   (def  temp (apply str '(remove-last startState) "," goalState))

;    )
;  )

(defn plan-route []

  (let [
        outputcmd (stateAdapter (apply str (:cmd (plan ss-one '(cas cargo-one station-two ) ops))))
        ]

    (println (count outputcmd))

    (loop [current outputcmd results []]

      (if (empty? current)
        results
        (let []
          ;;Rip out first
          ;;Split
          ;;Call a* start and make viable

          )
        (recur (rest current) (conj results "one")))
      )
    )
  )

(defn temp [outputcmd]
  (loop [xs outputcmd
         result []]
    (if xs
      (let [x (first xs)]
        (recur (next xs) (conj result (first xs))))
      result))
  )


  ;(def move (nth (clojure.string/split formatted #"move") (- x 1)))

  ;state planner
  ;goal planner
  ;a * map



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