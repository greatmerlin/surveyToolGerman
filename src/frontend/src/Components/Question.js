import React from "react";
import { Alert, Card, Form } from "react-bootstrap";
import { QUESTION_TYPES } from "../Config";
import AddButton from "./AddButton";
import AnswerOption from "./AnswerOption";
import DeleteButton from "./DeleteButton";

/**
 * Question box. Includes all options for user input for the question, question type and the corresponding answer options.
 *
 * @param {questionIndex} number
 * @param {removeQuestion} function
 * @param {answerOptions} function
 * @param {addAnswerOption} function
 * @param {removeAnswerOption} function
 * @param {questionValue} function
 * @param {updateQuestion} function
 * @param {updateAnswerOption} function
 * @param {updateQuestionType} function
 * @param {questionType} enum, MULTIPLE_CHOICE or MULTISELECT
 * @param {errorState} list of errors
 * @returns JSX.Element
 */

export default function Question({
  questionIndex,
  removeQuestion,
  answerOptions,
  addAnswerOption,
  removeAnswerOption,
  questionValue,
  updateQuestion,
  updateAnswerOption,
  updateQuestionType,
  questionType,
  errorState,
}) {
  const questionErrors = () => {
    return errorState.filter((error) => error.questionIndex === questionIndex);
  };

  return (
    <Card>
      <Card.Body>
        <Form.Group controlId={questionIndex}>
          <div className="d-flex align-items-center justify-content-between">
            <Form.Label className="h5 m-0">
              Frage #{questionIndex + 1}
            </Form.Label>
            <Form.Group className="d-flex align-items-center m-0">
              <Form.Label className="text-muted mb-0 mr-2">Typ</Form.Label>
              <Form.Control
                as="select"
                onChange={updateQuestionType}
                value={questionType}
              >
                <option value={QUESTION_TYPES.MULTIPLE_CHOICE}>
                  Einfachauswahl
                </option>
                <option value={QUESTION_TYPES.MULTISELECT}>
                  Mehrfachauswahl
                </option>
              </Form.Control>
            </Form.Group>
          </div>
          <Form.Text className="text-muted text-right mb-3">
            {questionType === QUESTION_TYPES.MULTIPLE_CHOICE
              ? "Teilnehmer muss exakt eine Antwort auswählen."
              : "Teilnehmer kann 0, 1 oder mehrere Antworten auswählen."}
          </Form.Text>
          <div
            className="position-absolute"
            style={{
              right: "-20px",
              top: "-20px",
            }}
          >
            <DeleteButton hint="Frage löschen" action={removeQuestion} />
          </div>
          <Form.Control
            type="text"
            placeholder="Erfasse hier eine Frage."
            value={questionValue}
            onChange={updateQuestion}
            required
          />
          <Form.Control.Feedback type="invalid">
            Das Feld darf nicht leer sein.
          </Form.Control.Feedback>
        </Form.Group>
        <hr />
        <h5 className="mb-3">Antworten</h5>
        {answerOptions.map((answerOption, i) => (
          <AnswerOption
            key={i}
            answerOptionValue={answerOption}
            answerOptionIndex={`${questionIndex}-${i}`}
            removeAnswerOption={() => removeAnswerOption(i)}
            updateAnswerOption={(() => updateAnswerOption(i))()}
          />
        ))}
        {questionErrors().length > 0 && (
          <Alert variant="danger">
            {questionErrors().map((error) => error.errorMsg)}
          </Alert>
        )}
        <AddButton hint="Antwort hinzufügen" action={addAnswerOption} />
      </Card.Body>
    </Card>
  );
}
