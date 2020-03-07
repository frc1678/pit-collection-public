// Copyright (c) 2019 FRC Team 1678: Citrus Circuits
package com.frc1678.pit_collection

class Constants {
    enum class ModeSelection {
        OBJECTIVE,
        SUBJECTIVE,
        NONE
    }

    data class DataObjective(
        var team_number: Int?,
        var can_cross_trench: Boolean?,
        var drivetrain: Int?,
        var has_ground_intake: Boolean?,
        var drivetrain_motors: Int?,
        var drivetrain_motor_type: Int?
    )

    data class DataSubjective(
        var team_number: Int?,
        var climber_strap_installation_time: Int?,
        var climber_strap_installation_notes: String?
    )
}