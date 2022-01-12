import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-institution.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankInstitutionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankInstitutionEntity = useAppSelector(state => state.bankInstitution.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankInstitutionDetailsHeading">
          <Translate contentKey="akountsApp.bankInstitution.detail.title">BankInstitution</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.id}</dd>
          <dt>
            <span id="institutionLabel">
              <Translate contentKey="akountsApp.bankInstitution.institutionLabel">Institution Label</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.institutionLabel}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="akountsApp.bankInstitution.code">Code</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.code}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="akountsApp.bankInstitution.active">Active</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="akountsApp.bankInstitution.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.currency}</dd>
          <dt>
            <span id="isoCountryCode">
              <Translate contentKey="akountsApp.bankInstitution.isoCountryCode">Iso Country Code</Translate>
            </span>
          </dt>
          <dd>{bankInstitutionEntity.isoCountryCode}</dd>
        </dl>
        <Button tag={Link} to="/bank-institution" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-institution/${bankInstitutionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankInstitutionDetail;
