import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-saving.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankSavingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankSavingEntity = useAppSelector(state => state.bankSaving.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankSavingDetailsHeading">
          <Translate contentKey="akountsApp.bankSaving.detail.title">BankSaving</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankSavingEntity.id}</dd>
          <dt>
            <span id="summaryDate">
              <Translate contentKey="akountsApp.bankSaving.summaryDate">Summary Date</Translate>
            </span>
          </dt>
          <dd>
            {bankSavingEntity.summaryDate ? <TextFormat value={bankSavingEntity.summaryDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.bankSaving.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{bankSavingEntity.amount}</dd>
          <dt>
            <span id="goal">
              <Translate contentKey="akountsApp.bankSaving.goal">Goal</Translate>
            </span>
          </dt>
          <dd>{bankSavingEntity.goal}</dd>
          <dt>
            <span id="reach">
              <Translate contentKey="akountsApp.bankSaving.reach">Reach</Translate>
            </span>
          </dt>
          <dd>{bankSavingEntity.reach}</dd>
        </dl>
        <Button tag={Link} to="/bank-saving" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-saving/${bankSavingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankSavingDetail;
