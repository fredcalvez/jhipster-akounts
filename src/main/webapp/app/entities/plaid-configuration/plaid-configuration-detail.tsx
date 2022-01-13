import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaid-configuration.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidConfigurationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaidConfigurationEntity = useAppSelector(state => state.plaidConfiguration.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaidConfigurationDetailsHeading">
          <Translate contentKey="akountsApp.plaidConfiguration.detail.title">PlaidConfiguration</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaidConfigurationEntity.id}</dd>
          <dt>
            <span id="environement">
              <Translate contentKey="akountsApp.plaidConfiguration.environement">Environement</Translate>
            </span>
          </dt>
          <dd>{plaidConfigurationEntity.environement}</dd>
          <dt>
            <span id="key">
              <Translate contentKey="akountsApp.plaidConfiguration.key">Key</Translate>
            </span>
          </dt>
          <dd>{plaidConfigurationEntity.key}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="akountsApp.plaidConfiguration.value">Value</Translate>
            </span>
          </dt>
          <dd>{plaidConfigurationEntity.value}</dd>
        </dl>
        <Button tag={Link} to="/plaid-configuration" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaid-configuration/${plaidConfigurationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaidConfigurationDetail;
