import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-account-interest.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankAccountInterestDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankAccountInterestEntity = useAppSelector(state => state.bankAccountInterest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankAccountInterestDetailsHeading">
          <Translate contentKey="akountsApp.bankAccountInterest.detail.title">BankAccountInterest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.id}</dd>
          <dt>
            <span id="interest">
              <Translate contentKey="akountsApp.bankAccountInterest.interest">Interest</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.interest}</dd>
          <dt>
            <span id="period">
              <Translate contentKey="akountsApp.bankAccountInterest.period">Period</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.period}</dd>
          <dt>
            <span id="interestType">
              <Translate contentKey="akountsApp.bankAccountInterest.interestType">Interest Type</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.interestType}</dd>
          <dt>
            <span id="units">
              <Translate contentKey="akountsApp.bankAccountInterest.units">Units</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.units}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="akountsApp.bankAccountInterest.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {bankAccountInterestEntity.startDate ? (
              <TextFormat value={bankAccountInterestEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="akountsApp.bankAccountInterest.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {bankAccountInterestEntity.endDate ? (
              <TextFormat value={bankAccountInterestEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="scrappingURL">
              <Translate contentKey="akountsApp.bankAccountInterest.scrappingURL">Scrapping URL</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.scrappingURL}</dd>
          <dt>
            <span id="scrappingTag">
              <Translate contentKey="akountsApp.bankAccountInterest.scrappingTag">Scrapping Tag</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.scrappingTag}</dd>
          <dt>
            <span id="scrappingTagBis">
              <Translate contentKey="akountsApp.bankAccountInterest.scrappingTagBis">Scrapping Tag Bis</Translate>
            </span>
          </dt>
          <dd>{bankAccountInterestEntity.scrappingTagBis}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankAccountInterest.creditedAccountId">Credited Account Id</Translate>
          </dt>
          <dd>{bankAccountInterestEntity.creditedAccountId ? bankAccountInterestEntity.creditedAccountId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-account-interest" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-account-interest/${bankAccountInterestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankAccountInterestDetail;
