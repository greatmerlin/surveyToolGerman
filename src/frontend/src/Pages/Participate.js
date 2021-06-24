import React, { useState, useEffect } from "react";
import { Spinner, Form, Button } from "react-bootstrap";
import Title from "../Components/Title";
import InsertCode from "../Components/InsertCode";
import { HOST, QUESTION_TYPES } from "../Config";
import QuestionParticipationBlock from "../Components/QuestionParticipationBlock";
import axios from "axios";
import MessageToast from "../Components/MessageToast";

/**
 * Page for participate in a survey.
 * Props contain an {id} param injected from the client-side router.
 *
 * @returns JSX.Element
 */

const Participate = (props) => {
  const urlId = props.match.params.id;
  const [data, setData] = useState();
  const [isSent, setIsSent] = useState(false);
  const [isValidId, setIsValidId] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [validated, setValidated] = useState(false);
  const [showToast, setShowToast] = useState({
    show: false,
    error: true,
    body: "",
  });

  useEffect(() => {
    fetch(`${HOST}/api/survey/${urlId}/`)
      .then((response) => response.json())
      .then((json) =>
        json.questions.map((question) => {
          question.answerOptions = question.answerOptions.map(
            (answerOption) => {
              return { ...answerOption, choosen: false };
            }
          );
          return question;
        })
      )
      .then((json) => {
        setIsLoading(false);
        setData(json);
        setIsValidId(true);
      })
      .catch((error) => {
        setIsLoading(false);
        setShowToast((prevState) => {
          const newState = {
            ...prevState,
            show: true,
            error: true,
            body: error.message,
          };
          return newState;
        });
        console.error({ error });
      });
  }, [urlId]);

  const isStateValide = (currentState) => {
    let isValid = true;

    currentState.forEach((question) => {
      if (question.questionType === QUESTION_TYPES.MULTIPLE_CHOICE) {
        const countChoosen = question.answerOptions.filter(
          (answerOption) => answerOption.choosen
        );
        if (countChoosen < 1) {
          isValid = false;
        }
      }
    });
    return isValid;
  };

  const submitForm = (event) => {
    event.preventDefault();
    if (isStateValide(data)) {
      const answerOptions = data
        .map((question) =>
          question.answerOptions
            .filter((answerOption) => answerOption.choosen)
            .map((answerOption) => answerOption.answerOptionId)
        )
        .reduce((acc, question) => acc.concat(question), []);
      axios
        .post(`${HOST}/api/survey/${urlId}/participation/`, { answerOptions })
        .then(() => {
          setIsSent(true);
        })
        .catch((error) => {
          setShowToast((prevState) => {
            const newState = {
              ...prevState,
              show: true,
              error: true,
              body: error.message,
            };
            return newState;
          });
          console.error({ error });
        });
    }
    setValidated(true);
  };

  const updateChoosen = (questionId) => {
    return (answerOptionId) => {
      setData((prevState) => {
        const newState = prevState.map((question) => {
          if (question.questionId !== questionId) {
            return question;
          }
          let newAnswerOptions = [];
          if (question.questionType === QUESTION_TYPES.MULTISELECT) {
            newAnswerOptions = question.answerOptions.map((answerOption) => {
              if (answerOption.answerOptionId === answerOptionId) {
                return { ...answerOption, choosen: !answerOption.choosen };
              }
              return answerOption;
            });
            return { ...question, answerOptions: newAnswerOptions };
          }
          newAnswerOptions = question.answerOptions.map((answerOption) => {
            return {
              ...answerOption,
              choosen: answerOption.answerOptionId === answerOptionId,
            };
          });
          return { ...question, answerOptions: newAnswerOptions };
        });
        return newState;
      });
    };
  };

  const closeToast = () => {
    setShowToast((prevState) => {
      const newState = { ...prevState, show: false };
      return newState;
    });
  };

  const showSpinner = () => {
    return (
      <Spinner animation="border" role="status" variant="primary">
        <span className="sr-only">Lädt...</span>
      </Spinner>
    );
  };

  const showCodeInput = () => {
    return <InsertCode url="participate" urlId={urlId} />;
  };

  const showParticipationForm = () => {
    return (
      <Form onSubmit={submitForm} noValidate validated={validated}>
        <div className="vertical-stack">
          {data.map((question, i) => (
            <QuestionParticipationBlock
              question={question}
              updateChoosen={(() => updateChoosen(question.questionId))()}
              questionIndex={i}
              key={i}
            />
          ))}
        </div>
        <Form.Group className="my-4 d-flex justify-content-end">
          <div>
            <Button href="/selectPath" variant="link" className="text-white">
              Abbrechen
            </Button>
            <Button type="submit">Teilnahme absenden</Button>
          </div>
        </Form.Group>
      </Form>
    );
  };

  const showParticipationFinished = () => {
    return (
      <>
        <p style={{ marginTop: "25vh" }}>Vielen Dank für deine Teilnahme!</p>
        <Button href="/" variant="primary" className="my-3">
          Zur Startseite
        </Button>
      </>
    );
  };

  const showState = () => {
    if (isSent) {
      return showParticipationFinished();
    }
    if (isLoading) {
      return showSpinner();
    }
    if (isValidId) {
      return showParticipationForm();
    }
    return showCodeInput();
  };

  return (
    <>
      <Title>Teilnahme</Title>
      {showState()}
      <MessageToast
        visibility={showToast.show}
        onClose={closeToast}
        error={showToast.error}
        body={showToast.body}
      />
    </>
  );
};

export default Participate;
