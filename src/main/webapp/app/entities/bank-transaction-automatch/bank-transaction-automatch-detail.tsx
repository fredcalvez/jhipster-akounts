import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-transaction-automatch.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTransactionAutomatchDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankTransactionAutomatchEntity = useAppSelector(state => state.bankTransactionAutomatch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankTransactionAutomatchDetailsHeading">
          <Translate contentKey="akountsApp.bankTransactionAutomatch.detail.title">BankTransactionAutomatch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankTransactionAutomatchEntity.id}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransactionAutomatch.transactionId">Transaction Id</Translate>
          </dt>
          <dd>{bankTransactionAutomatchEntity.transactionId ? bankTransactionAutomatchEntity.transactionId.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransactionAutomatch.automatchId">Automatch Id</Translate>
          </dt>
          <dd>{bankTransactionAutomatchEntity.automatchId ? bankTransactionAutomatchEntity.automatchId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-transaction-automatch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-transaction-automatch/${bankTransactionAutomatchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankTransactionAutomatchDetail;
