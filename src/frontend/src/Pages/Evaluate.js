import React, { useState, useEffect } from "react";
import { Spinner } from "react-bootstrap";
import Title from "../Components/Title";
import HyperlinkCards from "../Components/HyperlinkCards";
import InsertCode from "../Components/InsertCode";
import { HOST } from "../Config";
import QuestionStatBlock from "../Components/QuestionStatBlock";
import MessageToast from "../Components/MessageToast";

/**
 * Page displaying the summary of a survey.
 * Props contain an {id} param injected from the client-side router.
 *
 * @returns JSX.Element
 */

export default function Evaluate(props) {
  const urlId = props.match.params.id;
  const [state, setState] = useState();
  const [validId, setValidId] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [maxValue, setMaxValue] = useState(0);
  const [showToast, setShowToast] = useState({
    show: false,
    error: true,
    body: "",
  });

  useEffect(() => {
    fetch(`${HOST}/api/survey/${urlId}/result/`)
      .then((response) => response.json())
      .then((json) => {
        setIsLoading(false);
        if (json.questions) {
          setState(json);
          setValidId(true);
          const maxNumber = json.questions.reduce((acc, question) => {
            const localMax = question.answerOptions.reduce(
              (localAcc, answerOption) => {
                return localAcc < answerOption.count
                  ? answerOption.count
                  : localAcc;
              },
              0
            );
            return acc < localMax ? localMax : acc;
          }, 0);
          setMaxValue(maxNumber);
        }
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

  const closeToast = () => {
    setShowToast((prevState) => {
      const newState = { ...prevState, show: false };
      return newState;
    });
  };

  const showNewToast = (msg, error = true) => {
    setShowToast((prevState) => {
      const newState = {
        ...prevState,
        show: true,
        error: error,
        body: msg,
      };
      return newState;
    });
  };

  return (
    <>
      <Title>Deine Umfrage</Title>
      {isLoading ? (
        <Spinner animation="border" role="status" variant="primary">
          <span className="sr-only">Lädt...</span>
        </Spinner>
      ) : validId ? (
        <>
          <HyperlinkCards
            className="mb-4"
            participationLink={state.surveyId}
            evaluationLink={state.surveySecretId}
            showToast={showNewToast}
          />
          <div className="vertical-stack">
            <h3>Umfrageauswertung</h3>
            {state.questions.map((question, i) => (
              <QuestionStatBlock
                question={question}
                maxValue={maxValue}
                key={i}
              />
            ))}
          </div>
        </>
      ) : (
        <InsertCode url="evaluate" urlId={urlId} />
      )}
      <MessageToast
        visibility={showToast.show}
        onClose={closeToast}
        body={showToast.body}
        error={showToast.error}
      />
    </>
  );
}
