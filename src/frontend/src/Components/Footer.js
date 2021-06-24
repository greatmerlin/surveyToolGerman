import React from "react";
import { Button, Container } from "react-bootstrap";

/**
 * Footer component.
 *
 * @returns JSX.Element
 */

export default function Footer() {
	return (
		<footer className="fixed-bottom bg-dark text-white p-2">
			<Container>
				<div className="d-flex justify-content-between align-items-center">
					<p className="m-0">
						{new Date().getFullYear()}, All Rights Reserved by
						Fernfachhochschule Schweiz
					</p>
					<Button href="/faq" variant="outline-light" size="sm">
						FAQ
					</Button>
				</div>
			</Container>
		</footer>
	);
}
