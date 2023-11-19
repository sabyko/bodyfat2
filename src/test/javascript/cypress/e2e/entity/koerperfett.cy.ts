import { entityCreateButtonSelector, entityCreateSaveButtonSelector } from '../../support/entity';

describe('Koerperfett e2e test', () => {
  const koerperfettPageUrl = '/koerperfett';
  const koerperfettPageUrlPattern = new RegExp('/koerperfett(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/koerperfetts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/koerperfetts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/koerperfetts/*').as('deleteEntityRequest');
  });

  describe('Eingabeseite', () => {
    beforeEach(() => {
      cy.visit(`${koerperfettPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Koerperfett');
    });

    it('createKoerperfett männlich', () => {
      cy.get(`[data-cy="privatoderfirma"]`).should('not.be.checked');
      cy.get(`[data-cy="privatoderfirma"]`).click().should('be.checked');

      cy.get(`[data-cy="koerpergroesse"]`).type('190').should('have.value', '190');

      cy.get(`[data-cy="nackenumfang"]`).type('40').should('have.value', '40');

      cy.get(`[data-cy="bauchumfang"]`).type('90').should('have.value', '90');

      cy.get(`[data-cy="hueftumfang"]`).type('80').should('have.value', '80');

      cy.get(`[data-cy="geschlecht"]`).type('männlich').should('have.value', 'männlich');

      cy.get(`[data-cy="age"]`).type('35').should('have.value', '35');

      cy.get(`[data-cy="datumundZeit"]`).type('2023-11-06T06:39').blur().should('have.value', '2023-11-06T06:39');

      cy.get(`[data-cy="url"]`).type('testW.com').should('have.value', 'testW.com');

      cy.get(`[data-cy="success"]`).should('not.be.checked');
      cy.get(`[data-cy="success"]`).click().should('be.checked');

      cy.get(`[data-cy="errorMessage"]`).type('leer').should('have.value', 'leer');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', koerperfettPageUrlPattern);
    });
    it('createKoerperfett weiblich', () => {
      cy.get(`[data-cy="privatoderfirma"]`).should('not.be.checked');
      cy.get(`[data-cy="privatoderfirma"]`).click().should('be.checked');

      cy.get(`[data-cy="koerpergroesse"]`).type('170').should('have.value', '170');

      cy.get(`[data-cy="nackenumfang"]`).type('35').should('have.value', '35');

      cy.get(`[data-cy="bauchumfang"]`).type('70').should('have.value', '70');

      cy.get(`[data-cy="hueftumfang"]`).type('80').should('have.value', '80');

      cy.get(`[data-cy="geschlecht"]`).type('weiblich').should('have.value', 'weiblich');

      cy.get(`[data-cy="age"]`).type('25').should('have.value', '25');

      cy.get(`[data-cy="datumundZeit"]`).type('2023-11-06T06:39').blur().should('have.value', '2023-11-06T06:39');

      cy.get(`[data-cy="url"]`).type('testW.com').should('have.value', 'testW.com');

      cy.get(`[data-cy="success"]`).should('not.be.checked');
      cy.get(`[data-cy="success"]`).click().should('be.checked');

      cy.get(`[data-cy="errorMessage"]`).type('leer').should('have.value', 'leer');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', koerperfettPageUrlPattern);
    });
  });
});
