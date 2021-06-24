import React from "react";
import Title from "../Components/Title";
import { Button } from "react-bootstrap";

/**
 * Homepage.
 *
 * @returns JSX.Element
 */

export default function Home() {
  const introList = [
    "Erstelle einen Fragebogen mit einem benutzerfreundlichen und einfach verständlichen Umfragetool.",
    "Einfach verständliche Analyse von Umfrageergebnissen.",
    "Answerium, da oft Fragen auftauchen!",
  ];

  return (
    <>
      <Title>Online Umfragen</Title>
      <ul style={{ marginTop: "25vh" }}>
        {introList.map((entry, i) => {
          return (
            <li className="my-3" key={i}>
              <p className="lead">{entry}</p>
            </li>
          );
        })}
      </ul>
      <Button
        href="/selectPath"
        variant="primary"
        className="my-3"
        data-testid="testSelectPath"
      >
        Jetzt kostenlos starten!
      </Button>
    </>
  );
}
