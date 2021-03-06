/*
 * ScalaQCMS -- Scala Quantum Circuit Model Simulator
 *
 * Copyright (c) 2012 Antti Vikman
 */

package models

import solvers.{SolverStatistics, Solver, DummySolver}
/**
 * Solvers is a global container of all known solvers
 * @todo adding and removing of solvers on runtime
 */
object Solvers {

  val _solvers = Map[String, Solver](
    "dummy" -> DummySolver
  )

  def getSolvers = _solvers.keySet
  def getStatsOfSolvers(solvers: Set[String] = getSolvers): Map[String, SolverStatistics] = {
    var stats = Map.empty[String, SolverStatistics]
    for((name, solver) <- _solvers)
      if(solvers.contains(name))
        stats += ((name, solver.statistics))
    stats
  }

  def solve(equation: EquationEntity, solver: String = "dummy"): EquationEntity = {
    if(equation == null)
      throw new NullPointerException("Variable equation can not be null")
    if(!_solvers.contains(solver))
      throw new MatchError("No such solver as \"" + solver + "\"")

    _solvers.get(solver).get.solve(equation)
  }
}
