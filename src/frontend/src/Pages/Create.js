import React, { useState } from "react";
import Title from "../Components/Title";
import { Alert, Button, Form } from "react-bootstrap";
import Question from "../Components/Question";
import AddButton from "../Components/AddButton";
import { HOST, QUESTION_TYPES } from "../Config";
import axios from "axios";
import MessageToast from "../Components/MessageToast";

/**
 * Survey creation page.
 *
 * @returns JSX.Element
 */

export default function Create() {
  const emptyQuestionTemplate = {
    question: "",
    questionType: QUESTION_TYPES.MULTIPLE_CHOICE,
    answerOptions: ["", ""],
  };
  const [validated, setValidated] = useState(false);
  const [showToast, setShowToast] = useState({
    show: false,
    error: true,
    body: "",
  });
  const [state, setState] = useState({
    questions: [
      {
        ...emptyQuestionTemplate,
      },
    ],
  });
  const [errorState, setErrorState] = useState([]);

  const isStateValide = (currentState) => {
    let isValid = true;
    setErrorState([]);

    if (currentState.questions.length < 1) {
      setErrorState((prevState) => {
        const newState = [...prevState];
        newState.push({
          errorMsg: "Erfasse mindestens 1 Frage.",
        });
        return newState;
      });
      isValid = false;
    }

    currentState.questions.forEach((question, questionIndex) => {
      if (question.question === "") {
        isValid = false;
      }
      if (
        question.questionType === QUESTION_TYPES.MULTISELECT &&
        question.answerOptions.length < 1
      ) {
        setErrorState((prevState) => {
          const newState = [...prevState];
          newState.push({
            questionIndex: questionIndex,
            errorMsg: "Diese Frage benötigt mindestens 1 Antwort.",
          });
          return newState;
        });
        isValid = false;
      }
      if (
        question.questionType === QUESTION_TYPES.MULTIPLE_CHOICE &&
        question.answerOptions.length < 2
      ) {
        setErrorState((prevState) => {
          const newState = [...prevState];
          newState.push({
            questionIndex: questionIndex,
            errorMsg: "Diese Frage benötigt mindestens 2 Antworten.",
          });
          return newState;
        });
        isValid = false;
      }
      question.answerOptions.forEach((answer) => {
        if (answer === "") {
          isValid = false;
        }
      });
    });

    return isValid;
  };

  const submitForm = (event) => {
    event.preventDefault();
    if (isStateValide(state)) {
      axios
        .post(`${HOST}/api/survey/`, state)
        .then((response) => {
          window.location.replace(`/evaluate/${response.data.surveySecretId}`);
        })
        .catch((error) => {
          setShowToast((prevState) => {
            const newState = {
              ...prevState,
              show: true,
              body: error.message,
            };
            return newState;
          });
          console.error({ error });
        });
    }
    setValidated(true);
  };

  const addQuestion = () => {
    setState((prevState) => {
      const newState = [...prevState.questions];
      newState.push({ ...emptyQuestionTemplate });
      return { questions: newState };
    });
  };

  const removeQuestion = (index) => {
    setState((prevState) => {
      const newState = [...prevState.questions];
      newState.splice(index, 1);
      return { questions: newState };
    });
  };

  const addAnswerOption = (index) => {
    setState((prevState) => {
      const newState = prevState.questions.map((prevQuestion, i) => {
        if (i === index) {
          const newQuestion = {
            ...prevQuestion,
            answerOptions: [...prevQuestion.answerOptions, ""],
          };
          return newQuestion;
        }
        return prevQuestion;
      });
      return { questions: newState };
    });
  };

  const removeAnswerOption = (questionIndex) => {
    return (answerOptionIndex) => {
      setState((prevState) => {
        const newState = prevState.questions.map((prevQuestion, i) => {
          if (i === questionIndex) {
            const newAnswerOptions = [...prevQuestion.answerOptions];
            newAnswerOptions.splice(answerOptionIndex, 1);
            const newQuestion = {
              ...prevQuestion,
              answerOptions: newAnswerOptions,
            };
            return newQuestion;
          }
          return prevQuestion;
        });
        return { questions: newState };
      });
    };
  };

  const updateQuestion = (index) => {
    return (newValue) => {
      setState((prevState) => {
        const newState = [...prevState.questions];
        newState[index].question = newValue.target.value;
        return { questions: newState };
      });
    };
  };

  const updateAnswerOption = (questionIndex) => {
    return (answerOptionIndex) => {
      return (newValue) => {
        setState((prevState) => {
          const newState = [...prevState.questions];
          newState[questionIndex].answerOptions[answerOptionIndex] =
            newValue.target.value;
          return { questions: newState };
        });
      };
    };
  };

  const updateQuestionType = (questionIndex) => {
    return (newValue) => {
      setState((prevState) => {
        const newState = [...prevState.questions];
        newState[questionIndex].questionType = newValue.target.value;
        return { questions: newState };
      });
    };
  };

  const generalErrors = () => {
    return errorState.filter((error) => !error.hasOwnProperty("questionIndex"));
  };

  const closeToast = () => {
    setShowToast((prevState) => {
      const newState = { ...prevState, show: false };
      return newState;
    });
  };

  return (
    <>
      <Title>Erstelle eine Umfrage</Title>
      <Form onSubmit={submitForm} noValidate validated={validated}>
        <div className="vertical-stack">
          {state.questions.map((question, i) => (
            <Question
              key={i}
              questionIndex={i}
              answerOptions={question.answerOptions}
              questionValue={question.question}
              updateQuestion={(() => updateQuestion(i))()}
              removeQuestion={() => removeQuestion(i)}
              updateQuestionType={(() => updateQuestionType(i))()}
              addAnswerOption={() => addAnswerOption(i)}
              removeAnswerOption={(() => removeAnswerOption(i))()}
              updateAnswerOption={(() => updateAnswerOption(i))()}
              questionType={question.questionType}
              errorState={errorState}
            />
          ))}
        </div>
        {generalErrors().length > 0 && (
          <Alert variant="danger" className="mt-3">
            {generalErrors().map((error) => error.errorMsg)}
          </Alert>
        )}
        <Form.Group className="my-4 d-flex justify-content-between">
          <AddButton action={addQuestion} hint="Frage hinzufügen" />
          <div>
            <Button href="/selectPath" variant="link" className="text-white">
              Abbrechen
            </Button>
            <Button type="submit">Umfrage absenden</Button>
          </div>
        </Form.Group>
      </Form>
      <MessageToast
        visibility={false}
        onClose={closeToast}
        error={showToast.error}
        body={showToast.body}
      />
    </>
  );
}
