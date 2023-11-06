import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Koerperfett e2e test', () => {
  const koerperfettPageUrl = '/koerperfett';
  const koerperfettPageUrlPattern = new RegExp('/koerperfett(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const koerperfettSample = {
    privatoderfirma: false,
    koerpergroesse: 51625,
    nackenumfang: 53599,
    bauchumfang: 53322,
    geschlecht: 'open-source Home',
    age: 35709,
  };

  let koerperfett;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/koerperfetts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/koerperfetts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/koerperfetts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (koerperfett) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/koerperfetts/${koerperfett.id}`,
      }).then(() => {
        koerperfett = undefined;
      });
    }
  });

  it('Koerperfetts menu should load Koerperfetts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('koerperfett');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Koerperfett').should('exist');
    cy.url().should('match', koerperfettPageUrlPattern);
  });

  describe('Koerperfett page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(koerperfettPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Koerperfett page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/koerperfett/new$'));
        cy.getEntityCreateUpdateHeading('Koerperfett');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/koerperfetts',
          body: koerperfettSample,
        }).then(({ body }) => {
          koerperfett = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/koerperfetts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/koerperfetts?page=0&size=20>; rel="last",<http://localhost/api/koerperfetts?page=0&size=20>; rel="first"',
              },
              body: [koerperfett],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(koerperfettPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Koerperfett page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('koerperfett');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettPageUrlPattern);
      });

      it('edit button click should load edit Koerperfett page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Koerperfett');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettPageUrlPattern);
      });

      it.skip('edit button click should load edit Koerperfett page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Koerperfett');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettPageUrlPattern);
      });

      it('last delete button click should delete instance of Koerperfett', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('koerperfett').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettPageUrlPattern);

        koerperfett = undefined;
      });
    });
  });

  describe('new Koerperfett page', () => {
    beforeEach(() => {
      cy.visit(`${koerperfettPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Koerperfett');
    });

    it('should create an instance of Koerperfett', () => {
      cy.get(`[data-cy="privatoderfirma"]`).should('not.be.checked');
      cy.get(`[data-cy="privatoderfirma"]`).click().should('be.checked');

      cy.get(`[data-cy="koerpergroesse"]`).type('67769').should('have.value', '67769');

      cy.get(`[data-cy="nackenumfang"]`).type('76260').should('have.value', '76260');

      cy.get(`[data-cy="bauchumfang"]`).type('43653').should('have.value', '43653');

      cy.get(`[data-cy="hueftumfang"]`).type('33380').should('have.value', '33380');

      cy.get(`[data-cy="geschlecht"]`).type('grow Garden').should('have.value', 'grow Garden');

      cy.get(`[data-cy="age"]`).type('41668').should('have.value', '41668');

      cy.get(`[data-cy="koerperfettanteil"]`).type('54878').should('have.value', '54878');

      cy.get(`[data-cy="datumundZeit"]`).type('2023-11-06T06:39').blur().should('have.value', '2023-11-06T06:39');

      cy.get(`[data-cy="url"]`).type('https://leah.name').should('have.value', 'https://leah.name');

      cy.get(`[data-cy="success"]`).should('not.be.checked');
      cy.get(`[data-cy="success"]`).click().should('be.checked');

      cy.get(`[data-cy="errorMessage"]`).type('invoice Fresh navigate').should('have.value', 'invoice Fresh navigate');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        koerperfett = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', koerperfettPageUrlPattern);
    });
  });
});
