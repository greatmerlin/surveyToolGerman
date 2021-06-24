import React from "react";
import { Card, Form, ListGroup, Badge } from "react-bootstrap";
import { QUESTION_TYPES } from "../Config";
import BarChart from "./BarChart";

/**
 * Renders the summary of a question.
 *
 * @param {question} object, which consists of {question as string}, {questionType as enum (MULTIPLE_CHOICE or MULTISELECT)} and {answerOptions as list}.
 * @param {maxValue} number, maximum number of votes for any answer in the survey for context.
 * @returns  JSX.Element
 */

export default function QuestionStatBlock({ question, maxValue }) {
  const sumOfParticipations = question.answerOptions.reduce((acc, ao) => {
    return acc + ao.count;
  }, 0);

  return (
    <Card>
      <Card.Body>
        <Card.Title>{question.question}</Card.Title>
        <Form.Text className="text-muted mb-3">
          {question.questionType === QUESTION_TYPES.MULTIPLE_CHOICE
            ? "Einfachauswahl: Teilnehmer muss exakt eine Antwort auswählen."
            : "Mehrfachauswahl: Teilnehmer kann 0, 1 oder mehrere Antworten auswählen."}
        </Form.Text>
        <ListGroup variant="flush">
          {question.answerOptions.map((ao) => (
            <ListGroup.Item
              action
              key={ao.answerOptionId}
              style={{ cursor: "inherit" }}
            >
              <span
                style={{ position: "relative", zIndex: 10, display: "block" }}
              >
                <Badge variant="primary" className="mr-3">
                  {ao.count}
                </Badge>
                <strong>{ao.answerOption}</strong>
                {sumOfParticipations > 0 ? (
                  <span style={{ position: "absolute", top: 0, right: 0 }}>
                    {Math.round((ao.count / sumOfParticipations) * 100)} %
                  </span>
                ) : (
                  ""
                )}
              </span>
              <BarChart value={ao.count} max={maxValue} />
            </ListGroup.Item>
          ))}
        </ListGroup>
      </Card.Body>
    </Card>
  );
}
