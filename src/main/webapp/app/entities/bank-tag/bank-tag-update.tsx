import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankVendor } from 'app/shared/model/bank-vendor.model';
import { getEntities as getBankVendors } from 'app/entities/bank-vendor/bank-vendor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-tag.reducer';
import { IBankTag } from 'app/shared/model/bank-tag.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTagUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankVendors = useAppSelector(state => state.bankVendor.entities);
  const bankTagEntity = useAppSelector(state => state.bankTag.entity);
  const loading = useAppSelector(state => state.bankTag.loading);
  const updating = useAppSelector(state => state.bankTag.updating);
  const updateSuccess = useAppSelector(state => state.bankTag.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-tag');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankVendors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankTagEntity,
      ...values,
      vendor: bankVendors.find(it => it.id.toString() === values.vendor.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bankTagEntity,
          vendor: bankTagEntity?.vendor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankTag.home.createOrEditLabel" data-cy="BankTagCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankTag.home.createOrEditLabel">Create or edit a BankTag</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bank-tag-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('akountsApp.bankTag.tag')} id="bank-tag-tag" name="tag" data-cy="tag" type="text" />
              <ValidatedField
                label={translate('akountsApp.bankTag.useCount')}
                id="bank-tag-useCount"
                name="useCount"
                data-cy="useCount"
                type="text"
              />
              <ValidatedField
                id="bank-tag-vendor"
                name="vendor"
                data-cy="vendor"
                label={translate('akountsApp.bankTag.vendor')}
                type="select"
              >
                <option value="" key="0" />
                {bankVendors
                  ? bankVendors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-tag" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BankTagUpdate;
