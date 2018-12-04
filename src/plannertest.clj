;Experimentation with the planner to figure out how it works

(require 'search.planner)


(def ss3b
  '#{(station london)
     (station ncl)
     (station edin)
     (station duram)
     (station sunder)
     (isa ncl-exp train)
     (tas ncl-exp london)
     (cas llama ncl)

     (travable london ncl)
     (travable ncl london)

     (travable london duram)
     (travable duram london)

     (travable duram sunder)
     (travable sunder duram)

     (travable edin sunder)
     (travable sunder edin)

     })

(def ops3
  '{
    :unload
    {:name unload
     :achieves (cas ?stuff ?stn)
     :when ((isa ?t train))
     :post ((cot ?stuff ?t)(tas ?t ?stn))
     :pre ()
     :del ( (cot ?stuff ?t))
     :add ((cas ?stuff ?stn))
     :cmd ((drop ?stuff)  )
     :txt ((?t drops ?stuff at ?stn) )
     }
    :load
    {:name load
     :achieves (cot ?stuff ?t)
     :when ((cas ?stuff ?stn))
     :post ((tas ?t ?stn))
     :pre ()
     :del ((cas ?stuff ?stn) )
     :add ((cot ?stuff ?t) )
     :cmd ((pickup ?stuff))
     :txt ((?stuff picked up at ?stn by ?t))
     }
    :move
    {:name move
     :achieves (tas ?t ?dst)
     :when ((tas ?t ?src)(travable ?src ?dst))
     :post ()
     :pre ()
     :del ((tas ?t ?src))
     :add ((tas ?t ?dst))
     :cmd ((move ?t to ?dst))
     :txt ((?t moves to ?dst from ?src))
     }
    }
  )
;(planner start-state '((cas cargoOne stationOne)) ops-alt)
;(planner ss3b '(cas llama edin) ops3)

