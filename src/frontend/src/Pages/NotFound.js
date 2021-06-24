import React from "react";
import { Button } from "react-bootstrap";
import Title from "../Components/Title";

/**
 * 404 Page.
 *
 * @returns JSX.Element
 */

export default function NotFound() {
  return (
    <div style={{ marginTop: "25vh" }}>
      <Title>404: Seite nicht gefunden</Title>
      <Button href="/" variant="primary" className="my-3">
        Zur Startseite
      </Button>
    </div>
  );
}
