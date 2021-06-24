import React from "react";
import { CardDeck, Card, Button } from "react-bootstrap";

/**
 * Page describing the functionality of Answerium to the user.
 *
 * @returns JSX.Element
 */

export default function SelectPath() {
  const cards = [
    {
      title: "Umfrage erstellen",
      text:
        "Erstelle eine simple Umfrage ohne umständlich ein Konto anzulegen.",
      href: "/create",
      button: "Umfrage erstellen",
    },
    {
      title: "An einer Umfrage teilnehmen",
      text:
        "Du hast vom Umfrageersteller einen Link erhalten. Öffne den Link, um an der Umfrage teilzunehmen.",
    },
    {
      title: "Umfrage auswerten",
      text:
        "Nach der Erstellung deiner Umfrage erhältst du einen Link, um auf die Auswertung zugreifen zu können.",
    },
  ];

  return (
    <CardDeck style={{ marginTop: "25vh" }}>
      {cards.map((entry, i) => (
        <Card key={i}>
          <Card.Body>
            <Card.Title>{entry.title}</Card.Title>
            <Card.Text>{entry.text}</Card.Text>
          </Card.Body>
          {entry.href ? (
            <Card.Footer>
              <Button href={entry.href}>{entry.button}</Button>
            </Card.Footer>
          ) : (
            ""
          )}
        </Card>
      ))}
    </CardDeck>
  );
}
