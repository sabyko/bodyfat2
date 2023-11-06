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

describe('KoerperfettAvarage e2e test', () => {
  const koerperfettAvaragePageUrl = '/koerperfett-avarage';
  const koerperfettAvaragePageUrlPattern = new RegExp('/koerperfett-avarage(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const koerperfettAvarageSample = {};

  let koerperfettAvarage;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/koerperfett-avarages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/koerperfett-avarages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/koerperfett-avarages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (koerperfettAvarage) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/koerperfett-avarages/${koerperfettAvarage.id}`,
      }).then(() => {
        koerperfettAvarage = undefined;
      });
    }
  });

  it('KoerperfettAvarages menu should load KoerperfettAvarages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('koerperfett-avarage');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('KoerperfettAvarage').should('exist');
    cy.url().should('match', koerperfettAvaragePageUrlPattern);
  });

  describe('KoerperfettAvarage page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(koerperfettAvaragePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create KoerperfettAvarage page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/koerperfett-avarage/new$'));
        cy.getEntityCreateUpdateHeading('KoerperfettAvarage');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettAvaragePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/koerperfett-avarages',
          body: koerperfettAvarageSample,
        }).then(({ body }) => {
          koerperfettAvarage = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/koerperfett-avarages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/koerperfett-avarages?page=0&size=20>; rel="last",<http://localhost/api/koerperfett-avarages?page=0&size=20>; rel="first"',
              },
              body: [koerperfettAvarage],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(koerperfettAvaragePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details KoerperfettAvarage page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('koerperfettAvarage');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettAvaragePageUrlPattern);
      });

      it('edit button click should load edit KoerperfettAvarage page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('KoerperfettAvarage');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettAvaragePageUrlPattern);
      });

      it.skip('edit button click should load edit KoerperfettAvarage page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('KoerperfettAvarage');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettAvaragePageUrlPattern);
      });

      it('last delete button click should delete instance of KoerperfettAvarage', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('koerperfettAvarage').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', koerperfettAvaragePageUrlPattern);

        koerperfettAvarage = undefined;
      });
    });
  });

  describe('new KoerperfettAvarage page', () => {
    beforeEach(() => {
      cy.visit(`${koerperfettAvaragePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('KoerperfettAvarage');
    });

    it('should create an instance of KoerperfettAvarage', () => {
      cy.get(`[data-cy="geschlecht"]`).type('Avenue Freeway').should('have.value', 'Avenue Freeway');

      cy.get(`[data-cy="alter"]`).type('39254').should('have.value', '39254');

      cy.get(`[data-cy="koerperfettanteil"]`).type('53696').should('have.value', '53696');

      cy.get(`[data-cy="datumundZeit"]`).type('2023-11-06T06:57').blur().should('have.value', '2023-11-06T06:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        koerperfettAvarage = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', koerperfettAvaragePageUrlPattern);
    });
  });
});
