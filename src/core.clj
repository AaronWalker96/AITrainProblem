(require 'file2)
(require 'opsearch)
(require 'planner)
(require 'astar)

(println "CORE FILE LOADED")

(def world-state
  '#{(user Usr1)
     (user Usr2)
     (user Usr3)
     (lift Lift)})

(def start-state
  '#{(at Lift Flr2)
     (on Usr1 Flr1)
     (on Usr2 Flr3)
     (on Usr3 Flr3)})

(def ops
  '{enter-lift {:pre ((at ?l ?f)
                      (on ?u ?f))
                :add ((in ?u ?l))
                :del ((on ?u ?f))
                :txt (?u entered ?l on ?f)
                :cmd [enter ?u]}

    exit-lift {:pre ((at ?l ?f)
                     (in ?u ?l))
               :add ((on ?u ?f))
               :del ((in ?u ?l))
               :txt (?u left ?l on ?f)
               :cmd [exit ?u]}

    call-lift {:pre ((on ?u ?f1)
                     (at ?l ?f2)
                     (:not (called ?f1)))
               :add ((called ?f1))
               :del (())
               :txt (called ?l from ?f1)
               :cmd [call ?f1]}

    select-floor {:pre ((in ?u ?l)
                        (:not (called ?f)))
                  :add ((called ?f))
                  :del (())
                  :txt (selected ?f in ?l)
                  :cmd [select ?f]}

    wait-call {:pre ((called ?f2)
                     (at ?l ?f1)
                     (on ?u ?f2))
               :add ((at ?l ?f2))
               :del ((at ?l ?f1)
                     (called ?f2))
               :txt (?l call response on ?f2 by ?u)
               :cmd [move ?f2]}

    wait-select {:pre ((called ?f2)
                       (at ?l ?f1)
                       (in ?u ?l))
                 :add ((at ?l ?f2))
                 :del ((at ?l ?f1)
                       (called ?f2))
                 :txt (?l select response to ?f1 by ?u)
                 :cmd [move ?f2]}})

;(ops-search start-state '((on Usr1 Flr3) (on Usr2 Flr1)) ops :world world-state)
