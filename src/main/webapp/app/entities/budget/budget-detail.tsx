import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './budget.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BudgetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const budgetEntity = useAppSelector(state => state.budget.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="budgetDetailsHeading">
          <Translate contentKey="akountsApp.budget.detail.title">Budget</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{budgetEntity.id}</dd>
          <dt>
            <span id="budgetDate">
              <Translate contentKey="akountsApp.budget.budgetDate">Budget Date</Translate>
            </span>
          </dt>
          <dd>{budgetEntity.budgetDate ? <TextFormat value={budgetEntity.budgetDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="categorieLabel">
              <Translate contentKey="akountsApp.budget.categorieLabel">Categorie Label</Translate>
            </span>
          </dt>
          <dd>{budgetEntity.categorieLabel}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.budget.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{budgetEntity.amount}</dd>
          <dt>
            <Translate contentKey="akountsApp.budget.category">Category</Translate>
          </dt>
          <dd>{budgetEntity.category ? budgetEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/budget" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/budget/${budgetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BudgetDetail;
